package engteach.user.controller;

import engteach.user.entity.UserGameResource;
import engteach.user.mapper.UserGameResourceMapper;
import engteach.user.repository.UserGameResourceRepository;
import engteach.user.service.UserGameResourceService;
import engteach.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author codeofwhite
 * @date 2024/7/17
 * @Description
 */
@RestController
@RequestMapping("/userGameResource")
@CrossOrigin
public class UserGameResourceController {

    @Autowired
    private UserGameResourceRepository userGameResourceRepository;

    @Autowired
    private UserGameResourceService userGameResourceService;

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public UserGameResource getUserGameResource(@RequestParam String userEmail) {
        return userGameResourceRepository.getUserGameResourceByEmail(userEmail);
    }

    @PostMapping("/insert")
    public void insertUserGameResource(@RequestBody UserGameResource userGameResource) {
        userGameResource.setCreate_time(new Date());
        userGameResource.setUpdate_time(new Date());
        userGameResourceRepository.insertUserGameResource(userGameResource);
    }

    @PutMapping("/update")
    public void updateUserGameResource(@RequestBody UserGameResource userGameResource) {
        userGameResource.setUpdate_time(new Date());
        userGameResourceRepository.updateUserGameResource(userGameResource);
    }

    @PutMapping("/updateUserCoin")
    public String updateUserCoin(@RequestParam String userEmail, @RequestParam Integer userCoinChange) {
        return userGameResourceService.updateUserCoin(userEmail, userCoinChange);
    }

    @GetMapping("/getPunchInCount")
    public ResponseEntity<?> getPunchInCount(@RequestParam String userEmail) {
        int punchInCount = userService.getPunchInCount(userEmail);
        Map<String, Object> response = new HashMap<>();
        response.put("userEmail", userEmail);
        response.put("punchInCount", punchInCount);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/punchIn")
    public ResponseEntity<?> punchIn(@RequestBody Map<String, Object> payload) {
        String userEmail = (String) payload.get("userEmail");
        String result = userService.punchIn(userEmail);
        return ResponseEntity.ok(result);
    }
}
