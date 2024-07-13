package engteach.user.repository.Impl;

import engteach.user.entity.User;
import engteach.user.mapper.UserMapper;
import engteach.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author codeofwhite
 * @date 2024/6/26
 * @Description
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    UserMapper userMapper;

    @Override
    public User validateUser(String userEmail, String userPassword) {
        return userMapper.validateUser(userEmail, userPassword);
    }

    @Override
    public void register(String userName, String userEmail, String userPassword) {
        userMapper.register(userName, userEmail, userPassword);
    }
}
