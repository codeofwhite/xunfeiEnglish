package engteach.user.controller;

import engteach.user.entity.UserGameResource;
import engteach.user.mapper.UserGameResourceMapper;
import engteach.user.repository.UserGameResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

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
}
