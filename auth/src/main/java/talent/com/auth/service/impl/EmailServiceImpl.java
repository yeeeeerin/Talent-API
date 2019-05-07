package talent.com.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import talent.com.auth.service.EmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final String URL = "http://127.0.0.1:8080/members/";

    private final JavaMailSender emailSender;

    @Override
    public void sendMessage(String to, Long memberId) {

        String text = EmailServiceImpl.URL + memberId;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("[Talent] 인증을 진행해 주세요.");
        message.setText(text + " 이 주소를 클릭해 인증을 진행해 주세요.");
        emailSender.send(message);

    }
}
