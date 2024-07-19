package engteach.user.controller;

import engteach.user.entity.UserFavor;
import engteach.user.repository.UserFavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/19
 * @Description
 */
@RestController
@RequestMapping("/userFavor")
@CrossOrigin
public class UserFavorController {

    @Autowired
    private UserFavorService userFavorService;

    @PostMapping("/add")
    public ResponseEntity<String> addUserFavor(@RequestBody UserFavor userFavor) {
        try {
            userFavorService.insertUserFavor(userFavor);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public List<UserFavor> getAllUserFavors() {
        return userFavorService.getAllUserFavors();
    }
}
