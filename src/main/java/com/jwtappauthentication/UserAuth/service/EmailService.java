package com.jwtappauthentication.UserAuth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to,String Subject,int code){
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(Subject);
            mail.setText("Your OTP is: "+code);
            javaMailSender.send(mail);
            System.out.println("Your email is successfully sended");
        } catch (RuntimeException e) {
            System.out.println("Error in sending email"+e.getMessage());
        }


    }
}
