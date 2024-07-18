package engteach.user.mapper;

import engteach.user.entity.UserAchievement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/18
 * @Description
 */
@Mapper
public interface UserAchievementMapper {
    void insertUserAchievement(UserAchievement userAchievement);

    List<UserAchievement> getUserAchievementsByEmail(@Param("userEmail") String userEmail);
}
