package G3.jio.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("projectopenjio@gmail.com");
        mailSender.setPassword("project@OpenJio");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    // @Bean
    // public JavaMailSender getJavaMailSender() {
    // JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    // mailSender.setHost(System.getenv("MAIL_HOST"));
    // mailSender.setPort(Integer.parseInt(System.getenv("MAIL_PORT")));

    // mailSender.setUsername(System.getenv("MAIL_USERNAME"));
    // mailSender.setPassword(System.getenv("MAIL_PASSWORD"));

    // Properties props = mailSender.getJavaMailProperties();
    // props.put("mail.transport.protocol", "smtp");
    // props.put("mail.smtp.auth", "true");
    // props.put("mail.smtp.starttls.enable", "true");

    // return mailSender;
    // }
}
