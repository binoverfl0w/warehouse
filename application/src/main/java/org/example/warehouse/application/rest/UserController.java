package org.example.warehouse.application.rest;

import org.example.warehouse.application.rest.dto.OAuthTokenDTO;
import org.example.warehouse.application.rest.dto.PageDTO;
import org.example.warehouse.application.rest.dto.UserDTO;
import org.example.warehouse.application.security.jwt.JWTTokenUtil;
import org.example.warehouse.domain.user.User;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JWTTokenUtil jwtTokenUtil;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserDTO userDTO) {
        LOGGER.info("[POST /users/login] username = " + userDTO.getUsername() + ", password = " + userDTO.getPassword());
        // Attempt to authenticate via authentication manager
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate token for user
        String token = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        LOGGER.info("[POST /users/login] token = " + token);

        OAuthTokenDTO oAuthTokenDTO = new OAuthTokenDTO();
        oAuthTokenDTO.setToken(token);
        oAuthTokenDTO.setType("Bearer");
        oAuthTokenDTO.setExpiresIn(jwtTokenUtil.getValidPeriod());
        return ResponseEntity.ok(oAuthTokenDTO);
    }
}
