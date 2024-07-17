package engteach.user.service;

import engteach.user.entity.User;
import engteach.user.entity.UserGameResource;
import engteach.user.mapper.UserGameResourceMapper;
import engteach.user.mapper.UserMapper;
import engteach.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author codeofwhite
 * @date 2024/7/17
 * @Description
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGameResourceMapper userGameResourceMapper;

    private Map<String, UserGameResource> userMap = new HashMap<>();

    public String updateUsername(String userEmail, String newUsername) {
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            Date now = new Date();
            if (user.getLast_username_change() == null ||
                    now.getTime() - user.getLast_username_change().getTime() >= TimeUnit.DAYS.toMillis(30)) {
                userRepository.updateUsernameByEmail(userEmail, newUsername, now);
                return "用户名更新成功";
            } else {
                return "每月只能修改一次用户名";
            }
        } else {
            return "用户不存在";
        }
    }

    // 获取用户连续签到数
    public int getPunchInCount(String userEmail) {
        UserGameResource user = userGameResourceMapper.getUserGameResourceByEmail(userEmail);
        return user != null ? user.getPunch_in_count() : 0;
    }

    // 用户签到逻辑
    public String punchIn(String userEmail) {
        UserGameResource user = userGameResourceMapper.getUserGameResourceByEmail(userEmail);
        Date now = new Date();

        if (user == null) {
            user = new UserGameResource();
            user.setUser_email(userEmail);
            user.setPunch_in_count(1);
            user.setLast_punch(now);
            userGameResourceMapper.insert(user);
            return "连续签到成功";
        } else {
            Date lastPunch = user.getLast_punch();
            long diffInMillies = Math.abs(now.getTime() - lastPunch.getTime());
            long diffInHours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (diffInHours < 24 && isSameDay(now, lastPunch)) {
                // 已经签到过
                return "今天已经签过到";
            } else if (diffInHours >= 48) {
                // 超过48小时未签到，重置连续签到次数
                user.setPunch_in_count(1);
                user.setLast_punch(now);
                userGameResourceMapper.updateUserGameResource(user);
                return "连续48个小时未签到，重置签到次数";
            } else {
                // 连续签到
                user.setPunch_in_count(user.getPunch_in_count() + 1);
                user.setLast_punch(now);
                userGameResourceMapper.updateUserGameResource(user);
                return "连续签到成功";
            }
        }
    }

    private boolean isSameDay(Date date1, Date date2) {
        // 实现判断两个日期是否是同一天的逻辑
        return date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(
                date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}
