package engteach.user.controller;

import engteach.user.email.EmailService;
import engteach.user.entity.User;
import engteach.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author codeofwhite
 * @date 2024/6/26
 * @Description
 */
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sendVerificationCode")
    public ResponseEntity<Map<String, Object>> sendVerificationCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Map<String, Object> response = new HashMap<>();
        if (email == null || email.isEmpty()) {
            response.put("success", false);
            response.put("message", "邮箱地址不能为空");
            return ResponseEntity.badRequest().body(response);
        }
        emailService.sendVerificationCode(email);
        response.put("success", true);
        response.put("message", "验证码已发送");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        if (emailService.verifyCode(email, code)) {
            return ResponseEntity.ok("验证码正确");
        } else {
            return ResponseEntity.badRequest().body("验证码错误");
        }
    }

    @PostMapping("/getUserByEmail")
    public User getUserByEmail(@RequestParam String userEmail) {
        return userRepository.validateUser(userEmail, "");
    }
}
