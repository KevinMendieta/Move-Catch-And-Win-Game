package edu.eci.arsw.game.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService {

    private JavaMailSender sender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.sender = mailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        this.sender.send(email);
    }
}
