package org.example.warehouse.application.security.jwt;

import org.example.warehouse.application.rest.dto.UserDTO;
import org.example.warehouse.domain.user.IUserStore;
import org.example.warehouse.domain.user.User;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JWTTokenFilter extends OncePerRequestFilter {

    private final JWTTokenUtil jwtTokenUtil;
    private final IUserStore userStore;

    public JWTTokenFilter(JWTTokenUtil jwtTokenUtil,
                          IUserStore userStore) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userStore = userStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        Optional<User> user = userStore.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
        UserDetails userDetails = null;
        if (user.isPresent()) userDetails = user.map(UserDTO::fromDomainUser).get();
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                        List.of() : userDetails.getAuthorities()
        );
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

}

