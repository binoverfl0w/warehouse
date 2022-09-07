package org.example.warehouse.application.rest;

import org.example.warehouse.application.rest.dto.OAuthTokenDTO;
import org.example.warehouse.application.rest.dto.PageDTO;
import org.example.warehouse.application.rest.dto.UserDTO;
import org.example.warehouse.application.security.jwt.JWTTokenUtil;
import org.example.warehouse.domain.user.User;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.infrastructure.EmailService;
import org.example.warehouse.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private EmailService emailService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JWTTokenUtil jwtTokenUtil;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
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

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        LOGGER.info("[POST /users] fullname = " + userDTO.getFullname() +
                ", username = " + userDTO.getUsername() +
                ", email = " + userDTO.getEmail() +
                ", password = " + userDTO.getPassword() +
                ", role = " + userDTO.getRole());
        if (userDTO.getPassword() != null) userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return ResponseEntity.ok(UserDTO.fromDomainUser(userService.createUser(userDTO.toDomainUser())));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        LOGGER.info("[PUT /users] id = " + userDTO.getId() +
                ", fullname = " + userDTO.getFullname() +
                ", username = " + userDTO.getUsername() +
                ", email = " + userDTO.getEmail() +
                ", password = " + userDTO.getPassword() +
                ", role = " + userDTO.getRole());
        userDTO.setId(id);
        if (userDTO.getPassword() != null) userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return ResponseEntity.ok(UserDTO.fromDomainUser(userService.updateUser(userDTO.toDomainUser())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        LOGGER.info("[DELETE /users/" + id + "] id = " + id);
        userService.deleteUser(id);
        return new ResponseEntity<>(ResponseEntity.EMPTY, ResponseEntity.EMPTY.getHeaders(), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/reset")
    public ResponseEntity<Object> requestNewPassword() throws MessagingException {
        String randomKey = UUID.randomUUID().toString().replaceAll("_","");
        // Get the user that wants to reset the password
        User toReset = userService.getUser(userService.getAuthenticatedUser().getId());
        if (toReset.canResetPassword()) {
            EmailService.Email email = new EmailService.Email.EmailBuilder()
                    .from("test@gmail.com")
                    .to(toReset.getEmailAddress().getValue())
                    .subject("Reset password!")
                    .message("Your password reset link: " + "http://localhost:8443/changepassword?verify=" + randomKey + " is valid for one hour")
                    .build();
            LOGGER.info("[EMAIL] link = " + "http://localhost:8443/changepassword?verify=" + randomKey);
            emailService.sendEmail(email);
        } else
            throw new IllegalStateException("You can attempt to reset your password only once per hour");
        userService.requestNewPassword(randomKey);
        return ResponseEntity.ok(null);
    }
}
