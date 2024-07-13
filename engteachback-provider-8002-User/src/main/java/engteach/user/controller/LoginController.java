package engteach.user.controller;

import engteach.user.entity.User;
import engteach.user.model.LoginRequest;
import engteach.user.model.LoginResponse;
import engteach.user.model.RegisterRequest;
import engteach.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author codeofwhite
 * @date 2024/7/12
 * @Description
 */
@RestController
@RequestMapping("user")
@CrossOrigin
public class LoginController {

    @Autowired
    // 假设有一个用户服务来验证用户信息
    private UserRepository userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 验证用户名和密码
        User user = userService.validateUser(loginRequest.getUserEmail(), loginRequest.getUserPassword());
        System.out.println(user.getId());
        System.out.println(user.getUser_password());
        if (Objects.equals(loginRequest.getUserEmail(), user.getUser_email())) {
            // 登录成功
            if (Objects.equals(loginRequest.getUserPassword(), user.getUser_password())) {
                return ResponseEntity.ok(new LoginResponse(user.getUser_email(), true));
            } else {
                // 登录失败
                return ResponseEntity.ok(new LoginResponse("密码错误", false));
            }
        } else {
            // 登录失败
            return ResponseEntity.ok(new LoginResponse("没有此用户", false));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        // 验证用户名和密码
        User user = userService.validateUser(registerRequest.getUserEmail(), registerRequest.getUserPassword());
        if (user != null) {
            // 注册失败，用户已存在
            return ResponseEntity.ok(new LoginResponse("", false));
        } else {
            // 注册成功
            userService.register(registerRequest.getUserName(), registerRequest.getUserEmail(), registerRequest.getUserPassword());
            return ResponseEntity.ok(new LoginResponse(registerRequest.getUserEmail(), true));
        }
    }
}
