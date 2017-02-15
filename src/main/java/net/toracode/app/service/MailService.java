package net.toracode.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by sayemkcn on 2/14/17.
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String email,String message) throws Exception{
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Response from Autism Barta");
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

}
