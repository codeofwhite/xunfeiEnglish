package engteach.user.repository;

import engteach.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author codeofwhite
 * @date 2024/6/26
 * @Description
 */
public interface UserRepository {
    User validateUser(String userEmail, String userPassword);

    void register(String userName, String userEmail, String userPassword);

    void updateUsernameByEmail(String userEmail, String newUsername, Date lastUsernameChange);

    User findByEmail(String userEmail);
}
