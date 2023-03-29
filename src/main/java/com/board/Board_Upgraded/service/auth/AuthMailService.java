package com.board.Board_Upgraded.service.auth;

import com.board.Board_Upgraded.dto.auth.EmailAuthRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthMailService {

    private final JavaMailSender mailSender;
    private static final String SENDER = "janghee5395@gmail.com";
    private static final String TITLE = "L.A.N 회원가입 인증 번호";
    private String authNum;

    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(TITLE);
        message.setFrom(SENDER);
        createCode();
        message.setText("인증 번호는 " + authNum + "입니다.");
        return message;
    }

    public String sendEmail(EmailAuthRequestDto toEmail) throws MessagingException, UnsupportedEncodingException {
        MimeMessage emailForm = createEmailForm(toEmail.getEmail());
        mailSender.send(emailForm);
        return authNum; //인증 코드 반환
    }

    private void createCode(){
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for(int i=0;i<8;i++) {
            int index = random.nextInt(3);
            switch (index) {
                case 0 :
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }
}
