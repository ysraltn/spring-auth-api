package tech.ysraltn.backend_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Şifre Sıfırlama Kodu");
        message.setText("Şifre sıfırlama kodunuz: " + code);
        message.setFrom("noreply@authapi.com");
        mailSender.send(message);
    }
}
