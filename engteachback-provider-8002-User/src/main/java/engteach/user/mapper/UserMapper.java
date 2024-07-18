package engteach.user.mapper;

import engteach.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * @author codeofwhite
 * @date 2024/6/26
 * @Description
 */
@Mapper
public interface UserMapper {
    User validateUser(@Param("userEmail") String userEmail, @Param("userPassword") String userPassword);

    void register(@Param("userName") String userName, @Param("userEmail") String userEmail, @Param("userPassword") String userPassword);

    @Update("UPDATE user_info SET user_name = #{newUsername}, last_username_change = #{lastUsernameChange} WHERE user_email = #{userEmail}")
    void updateUsernameByEmail(@Param("userEmail") String userEmail, @Param("newUsername") String newUsername, @Param("lastUsernameChange") Date lastUsernameChange);

    @Select("SELECT * FROM user_info WHERE user_email = #{userEmail}")
    User findByEmail(String userEmail);

    void updateAvatarByEmail(@Param("userEmail") String userEmail, @Param("userAvatar") String userAvatar);
}
