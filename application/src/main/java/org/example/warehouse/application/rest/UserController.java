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

    private UserService userService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JWTTokenUtil jwtTokenUtil;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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


    @GetMapping
    public ResponseEntity<Object> getUsers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        LOGGER.info("[GET /users] page = " + page + ", size = " + size);

        if (page == null) page = 0;
        if (size == null) size = 20;

        PageVO<User> users = userService.getUserPage(page, size);
        PageDTO<UserDTO> usersDTO = new PageDTO<>();
        usersDTO.setItems(users.getItems().stream().map(UserDTO::fromDomainUser).collect(Collectors.toList()));
        usersDTO.setTotalItems(users.getTotalItems());
        usersDTO.setTotalPages(users.getTotalPages());
        usersDTO.setCurrentPage(users.getCurrentPage());
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        LOGGER.info("[GET /users/" + id + "] id = " + id);
        return ResponseEntity.ok(UserDTO.fromDomainUser(userService.getUser(id)));
    }
}
