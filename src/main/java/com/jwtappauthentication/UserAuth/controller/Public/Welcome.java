package com.jwtappauthentication.UserAuth.controller.Public;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user-auth")
public class Welcome {

    @GetMapping("/welcome")
    public String welcomeMessage(){
        System.out.println("Your Most Welcome to this USER AUTH App!");
        return "Welcome to USER AUTH APP";

    }
}
