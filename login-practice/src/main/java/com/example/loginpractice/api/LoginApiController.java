package com.example.loginpractice.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loginpractice.domain.User;
import com.example.loginpractice.service.UserService;
import com.example.loginpractice.service.dto.JoinRequest;
import com.example.loginpractice.service.dto.LoginRequest;

@RestController
@RequestMapping("/jwt-login")
public class LoginApiController {

    private final UserService userService;

    public LoginApiController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<User> join(@RequestBody JoinRequest request) {
        if (userService.checkUsernameDuplicate(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (userService.checkNicknameDuplicate(request.getNickname())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userService.join(request);

        return ResponseEntity.ok(userService.findByUsername(request.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        User user = userService.login(request);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }

}
