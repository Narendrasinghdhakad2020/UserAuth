package com.jwtappauthentication.UserAuth.filter;

import com.jwtappauthentication.UserAuth.model.UserDetails;
import com.jwtappauthentication.UserAuth.service.UserDetailService;
import com.jwtappauthentication.UserAuth.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    @Lazy
    private JwtUtils jwtUtils;

    @Autowired
    @Lazy
    private UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String authorizationHeader=request.getHeader("Authorization"); //get the authorization header
        String token = null;
        String username=null;
        System.out.println("Hello: I am in JwtFilter class");
        //check if the authorization header contain JWT Bearer token
        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
            token =authorizationHeader.substring(7); // remove the prefix "Bearer "
            username= jwtUtils.extractUsername(token); // extract username from the token
        }

        //validate the token and set security context
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailService.loadUserByUserName(username);//loading the db user
            System.out.println("DB username is: "+userDetails.getUsername());
            System.out.println("Provided username is: "+username);


            if(jwtUtils.validateToken(token,userDetails.getUsername())){
                System.out.println("Validating the token");
                UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities());
                System.out.println("Auth token is: "+authtoken);
                authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authtoken);// set user authentication in security context
                System.out.println("User context is successfully set to the security context for authentication");
            }
        }
        chain.doFilter(request,response); // continue the filter chain

    }


}
