package G3.jio.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    final private JavaMailSender javaMailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("projectopenjio@outlook.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(String.format(
                "[THIS IS AN AUTOMATED MESSAGE - PLEASE DO NOT REPLY DIRECTLY TO THIS EMAIL]\n\nHere is your reset password key: %s%n%nPlease paste this key in the app to reset your password%n",
                text));

        javaMailSender.send(message);
    }
}
