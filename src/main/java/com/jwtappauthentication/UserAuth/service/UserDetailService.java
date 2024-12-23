package com.jwtappauthentication.UserAuth.service;

import com.jwtappauthentication.UserAuth.dto.AuthRequest;
import com.jwtappauthentication.UserAuth.model.UserDetails;
import com.jwtappauthentication.UserAuth.repository.UserDetailRepository;
import com.jwtappauthentication.UserAuth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.DoubleSummaryStatistics;

@Service
public class UserDetailService   {
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    @Lazy
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUserName(String username){
        System.out.println("Service: Loading user from database!");
        return userDetailRepository.findUserByUsername(username);
    }

    public void saveUserInDb(UserDetails user){
        System.out.println("Service: Saving user to database");
        userDetailRepository.save(user);
    }
    public boolean exitsByUserName(String username){
        return userDetailRepository.existsUserByUsername(username);
    }

    @Transactional
    public String login(AuthRequest request, AuthenticationManager authenticationManager){
        System.out.println("We are in login service!");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUser(), request.getPass());
        System.out.println("Auth token is : "+authToken);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUser(),request.getPass()));
        System.out.println("User is successfully Authenticated");
        UserDetails userDetails = userDetailRepository.findUserByUsername(request.getUser());
        System.out.println("User Details fetched are: "+userDetails);
        return jwtUtils.generateToken(userDetails.getUsername());
    }

}
