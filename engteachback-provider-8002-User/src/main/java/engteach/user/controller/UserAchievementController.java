package engteach.user.controller;

import engteach.user.entity.UserAchievement;
import engteach.user.repository.UserAchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/18
 * @Description
 */
@RestController
@RequestMapping("/api/user/achievements")
@CrossOrigin
public class UserAchievementController {

    @Autowired
    private UserAchievementRepository userAchievementRepository;

    @PostMapping
    public void insertUserAchievement(@RequestBody UserAchievement userAchievement) {
        userAchievementRepository.insertUserAchievement(userAchievement);
    }

    @GetMapping("/{userEmail}")
    public List<UserAchievement> getUserAchievementsByEmail(@PathVariable String userEmail) {
        return userAchievementRepository.getUserAchievementsByEmail(userEmail);
    }
}
