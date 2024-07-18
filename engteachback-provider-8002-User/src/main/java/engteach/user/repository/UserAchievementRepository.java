package engteach.user.repository;

import engteach.user.entity.UserAchievement;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/18
 * @Description
 */
public interface UserAchievementRepository {
    void insertUserAchievement(UserAchievement userAchievement);

    List<UserAchievement> getUserAchievementsByEmail(String userEmail);
}
