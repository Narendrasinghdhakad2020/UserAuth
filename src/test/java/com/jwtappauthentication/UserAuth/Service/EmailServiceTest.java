package com.jwtappauthentication.UserAuth.Service;

import com.jwtappauthentication.UserAuth.UserAuthApplication;
import com.jwtappauthentication.UserAuth.service.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest(classes = UserAuthApplication.class)
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    @Disabled
    public void testSendEmail(){
        Random random = new Random();
        int code=100000+random.nextInt(900000);
        emailService.sendEmail("aditya7730@gmail.com","Verification Email",code);

    }
}
