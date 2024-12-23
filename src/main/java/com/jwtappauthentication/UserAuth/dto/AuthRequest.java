package com.jwtappauthentication.UserAuth.dto;

public class AuthRequest {
    private String user;
    private String pass;
    //getters and setters
    public void setUser(String user){
        this.user=user;
    }
    public String getUser(){
        return user;
    }
    public void setPass(String pass){
        this.pass=pass;
    }
    public String getPass(){
        return pass;
    }

}
