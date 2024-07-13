package engteach.user.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> verificationCodes = new HashMap<>();

    public void sendVerificationCode(String to) {
        String code = String.format("%06d", new Random().nextInt(999999));
        verificationCodes.put(to, code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1363180320@qq.com"); // 确保这个地址与授权用户地址相同，不如会报错，Gmail就不需要
        message.setTo(to);
        message.setSubject("您的验证码");
        message.setText("您的验证码是：" + code);
        mailSender.send(message);
    }

    public boolean verifyCode(String email, String code) {
        return code.equals(verificationCodes.get(email));
    }
}