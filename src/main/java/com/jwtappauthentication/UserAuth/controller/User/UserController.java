package com.jwtappauthentication.UserAuth.controller.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/greet")
    public ResponseEntity<?> greet(){
        System.out.println("Welcome to user route");
        return new ResponseEntity<>("Welcome to user route", HttpStatus.OK);
    }
}
