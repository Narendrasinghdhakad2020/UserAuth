package com.jwtappauthentication.UserAuth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtUtils {
    //This component is used to generate and validate the token
    //method to generate strong secrete key
    public static String SecreteKeyGenerator(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32]; // 256-bit key (32 bytes)
        secureRandom.nextBytes(key);

        // Encode the byte array as a Base64 string
        String base64Key = Base64.getEncoder().encodeToString(key);

        // Print the Base64-encoded key
        System.out.println("Generated Secret Key (Base64): " + base64Key);
        return base64Key;
    }

    private final String SECRET_KEY=SecreteKeyGenerator(); //signing key for token generation
    private final long TOKEN_VALIDITY=5*60*1000; //5 minute validity

    //Generate Token
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username) //user info in token payload
                .setIssuedAt(new Date()) //token creation date
                .setExpiration(new Date(System.currentTimeMillis()+TOKEN_VALIDITY)) //expiry date
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY) //signing algorithm and secret key
                .compact(); //builds and returns a string token
    }

    //validate token
    public boolean validateToken(String token,String username){
        System.out.println("Inside the validateToken in jwtUtils");
        System.out.println("Utils ValidateToken : user  "+username+" and token "+token);
        String tokenUsername=extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    //method to extract Username
    public String extractUsername(String token){
        System.out.println("Extracting the username from token ");
        Claims claims=Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        System.out.println("Extracted username from token is: "+claims.getSubject());
        return claims.getSubject();
    }

    //check if the token is expired or not
    public boolean isTokenExpired(String token){
        System.out.println("Checking is token expired");
        return extractExpiration(token).before(new Date());
    }

    //extract the expiration data from token
    public Date extractExpiration(String token){
        System.out.println("Extracting the token expiration time");
        Claims claims=Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        System.out.println("Expiration time is: "+claims.getExpiration());
        return claims.getExpiration();
    }
}
