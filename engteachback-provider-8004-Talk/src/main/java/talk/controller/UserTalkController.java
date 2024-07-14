package talk.controller;

/**
 * @author codeofwhite
 * @date 2024/7/14
 * @Description
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import talk.entity.UserTalk;
import talk.service.UserTalkService;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/talk")
@CrossOrigin
public class UserTalkController {

    @Autowired
    private UserTalkService userTalkService;

    @PostMapping
    public UserTalk createTalk(@RequestBody UserTalk userTalk) {
        userTalk.setTalkId(UUID.randomUUID().toString());
        userTalk.setCreateTime(new Date());
        return userTalkService.save(userTalk);
    }
}
