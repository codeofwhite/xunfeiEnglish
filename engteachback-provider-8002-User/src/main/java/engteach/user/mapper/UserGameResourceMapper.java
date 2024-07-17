package engteach.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import engteach.user.entity.UserGameResource;
import org.apache.ibatis.annotations.*;

/**
 * @author codeofwhite
 * @date 2024/7/17
 * @Description
 */
@Mapper
public interface UserGameResourceMapper extends BaseMapper<UserGameResource> {

    UserGameResource getUserGameResourceByEmail(@Param("userEmail") String userEmail);

    void insertUserGameResource(UserGameResource userGameResource);

    @Update("UPDATE user_game_resource SET user_coin = user_coin + #{userCoinChange}, update_time = NOW() WHERE user_email = #{userEmail}")
    void updateUserCoinByEmail(@Param("userEmail") String userEmail, @Param("userCoinChange") Integer userCoinChange);

    @Select("SELECT * FROM user_game_resource WHERE user_email = #{userEmail}")
    UserGameResource findByEmail(String userEmail);

    @Update("<script>" +
            "UPDATE user_game_resource " +
            "<set>" +
            "<if test='user_coin != null'>user_coin = #{user_coin},</if>" +
            "<if test='user_spark != null'>user_spark = #{user_spark},</if>" +
            "<if test='user_level != null'>user_level = #{user_level},</if>" +
            "<if test='punch_in_count != null'>punch_in_count = #{punch_in_count},</if>" +
            "<if test='last_punch != null'>last_punch = #{last_punch},</if>" +
            "</set>" +
            "WHERE user_email = #{user_email}" +
            "</script>")
    void updateUserGameResource(UserGameResource userGameResource);
}
