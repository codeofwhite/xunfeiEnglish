package engteach.user.repository.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import engteach.user.entity.UserAchievement;
import engteach.user.mapper.UserAchievementMapper;
import engteach.user.repository.UserAchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/18
 * @Description
 */
@Repository
public class UserAchievementRepositoryImpl implements UserAchievementRepository {

    @Autowired
    private UserAchievementMapper userAchievementMapper;

    @Override
    public void insertUserAchievement(UserAchievement userAchievement) {
        userAchievementMapper.insertUserAchievement(userAchievement);
    }

    @Override
    public List<UserAchievement> getUserAchievementsByEmail(String userEmail) {
        return userAchievementMapper.getUserAchievementsByEmail(userEmail);
    }
}
