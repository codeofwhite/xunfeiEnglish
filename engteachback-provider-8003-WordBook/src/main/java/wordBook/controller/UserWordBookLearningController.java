package wordBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wordBook.service.UserLearningService;

/**
 * @author codeofwhite
 * @date 2024/7/18
 * @Description
 */
@RestController
@RequestMapping("/learning")
@CrossOrigin
public class UserWordBookLearningController {

    @Autowired
    private UserLearningService userLearningService;

    @GetMapping("/unknownWords/{userEmail}")
    public int getUnknownWordCount(@PathVariable String userEmail) {
        return userLearningService.getUnknownWordCount(userEmail);
    }

    @GetMapping("/masteredWords/{userEmail}")
    public int getMasteredWordCount(@PathVariable String userEmail) {
        return userLearningService.getMasteredWordCount(userEmail);
    }

    @GetMapping("/progress/{userEmail}")
    public double getLearningProgress(@PathVariable String userEmail) {
        return userLearningService.getLearningProgress(userEmail);
    }
}
