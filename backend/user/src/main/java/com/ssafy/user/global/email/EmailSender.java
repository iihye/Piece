package com.ssafy.user.global.email;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.security.SecureRandom;
import java.util.Properties;

@Component
public class EmailSender {
    private String type = "text/html; charset=utf-8";

    @Value("${spring.mail.address}")
    private String emailAddress;

    @Value("${spring.mail.password}")
    private String password;

    public void sendMail(String email, String title, String content) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailAddress, "Piece"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(title);
            message.setContent(content, type);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateCode() {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 10; i++) {
            sb.append((char) ((int) (random.nextDouble() * 57) + 65));
        }
        return sb.toString();
    }
}