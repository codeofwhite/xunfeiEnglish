package engteach.user.controller;

import engteach.user.email.EmailService;
import engteach.user.entity.User;
import engteach.user.repository.UserRepository;
import engteach.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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

    @Autowired
    private UserService userService;

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

    @PutMapping("/updateUsername")
    public String updateUsername(@RequestParam String userEmail, @RequestParam String newUsername) {
        return userService.updateUsername(userEmail, newUsername);
    }

    @PostMapping("/updateAvatar")
    public String updateAvatar(@RequestParam("userEmail") String userEmail, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 重新生成文件名
        fileName = UUID.randomUUID() + suffixName;

        // 文件存储路径
        String filePath = "path/to/your/avatar/directory/";
        File dest = new File(filePath + fileName);

        try {
            // 保存文件
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败";
        }

        // 更新用户头像路径
        userRepository.updateAvatarByEmail(userEmail, filePath + fileName);
        return "头像更新成功";
    }
}
