package com.jwtappauthentication.UserAuth.controller.Public;

import com.jwtappauthentication.UserAuth.dto.AuthRequest;
import com.jwtappauthentication.UserAuth.service.UserDetailService;
import com.jwtappauthentication.UserAuth.utils.JwtUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("user-auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) throws AuthenticationException{
        try{
            System.out.println("Request UserName: "+request.getUser());
            System.out.println("Request Password: "+request.getPass());
            String token = userDetailService.login(request,authenticationManager);
            if(token!=null){
                System.out.println("Token is generated successfully");
                System.out.println("Generated Token is: "+token);
                return new ResponseEntity<>("Token: "+token, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Token is empty",HttpStatus.BAD_REQUEST);
            }

        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}




