package tech.ysraltn.backend_api.config;

import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.internet.MimeMessage;

@Configuration
public class MailConfig {

    @Bean
    @Profile("dev")
    public JavaMailSender devMailSender() {
        return new JavaMailSender() {
            @Override
            public void send(SimpleMailMessage simpleMessage) {
                System.out.println("📧 (dev) Mail log: \nTo: " + String.join(", ", simpleMessage.getTo()));
                System.out.println("Subject: " + simpleMessage.getSubject());
                System.out.println("Body: " + simpleMessage.getText());
                //JavaMailSender sınıfından send fonksiyonunu override ederek maili sadece terminale basıyoruz
            }

			@Override
			public void send(SimpleMailMessage... simpleMessages) throws MailException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public MimeMessage createMimeMessage() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void send(MimeMessage... mimeMessages) throws MailException {
				// TODO Auto-generated method stub
				
			}

            // Diğer metotlar override edilmeden bırakılabilir (boş)-> bırakılamadı
        };
    }

    // production profilindeysen Spring, kendi mail sender'ını otomatik üretir (çünkü ayarlar var)
}
