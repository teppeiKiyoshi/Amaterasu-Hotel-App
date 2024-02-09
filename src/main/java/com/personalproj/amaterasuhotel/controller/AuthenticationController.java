package com.personalproj.amaterasuhotel.controller;

import com.personalproj.amaterasuhotel.exception.UserAlreadyExistsException;
import com.personalproj.amaterasuhotel.model.UserModel;
import com.personalproj.amaterasuhotel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/amaterasu/v1/api/auth")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(UserModel user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User was registered successfully");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }



}
