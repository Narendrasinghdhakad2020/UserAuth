package com.jwtappauthentication.UserAuth.controller.Public;

import com.jwtappauthentication.UserAuth.dto.SignupRequest;
import com.jwtappauthentication.UserAuth.model.UserDetails;
import com.jwtappauthentication.UserAuth.service.UserDetailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user-auth")
@NoArgsConstructor
@AllArgsConstructor
public class SignupController {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request){
        //check for user already exist or not
         boolean isUserExist=userDetailService.exitsByUserName(request.getUsername());
         if(!isUserExist){
             String encodedPassword=passwordEncoder.encode(request.getPassword());
             System.out.println("At the signup time Encoded password is: "+encodedPassword);
             System.out.println("Creating user in the DB");
             UserDetails user=new UserDetails();
             user.setUsername(request.getUsername());
             user.setPassword(encodedPassword);
             user.setRoles(request.getRoles());
             System.out.println("Roles are: "+request.getRoles());
             System.out.println("User is :"+user);
             userDetailService.saveUserInDb(user);
             System.out.println("User successfully saved into the database");
             return new ResponseEntity<>(user, HttpStatus.OK);
         }
         else{
             System.out.println("Username is already taken please enter new username!");
             return new ResponseEntity<>("Username: "+request.getUsername()+" is already taken please provide new username",HttpStatus.BAD_REQUEST);
         }

    }

}
