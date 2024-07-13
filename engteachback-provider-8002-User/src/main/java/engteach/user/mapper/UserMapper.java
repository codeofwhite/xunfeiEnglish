package engteach.user.mapper;

import engteach.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author codeofwhite
 * @date 2024/6/26
 * @Description
 */
@Mapper
public interface UserMapper {
    User validateUser(@Param("userEmail") String userEmail, @Param("userPassword") String userPassword);

    void register(@Param("userName") String userName, @Param("userEmail") String userEmail, @Param("userPassword") String userPassword);
}
