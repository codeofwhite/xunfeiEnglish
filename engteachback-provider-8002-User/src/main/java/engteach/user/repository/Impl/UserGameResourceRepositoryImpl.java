package engteach.user.repository.Impl;

import engteach.user.entity.UserGameResource;
import engteach.user.mapper.UserGameResourceMapper;
import engteach.user.repository.UserGameResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author codeofwhite
 * @date 2024/7/17
 * @Description
 */
@Repository
public class UserGameResourceRepositoryImpl implements UserGameResourceRepository {

    @Autowired
    UserGameResourceMapper userGameResourceMapper;

    @Override
    public UserGameResource getUserGameResourceByEmail(String userEmail) {
        return userGameResourceMapper.getUserGameResourceByEmail(userEmail);
    }

    @Override
    public void insertUserGameResource(UserGameResource userGameResource) {
        userGameResourceMapper.insertUserGameResource(userGameResource);
    }

    @Override
    public void updateUserGameResource(UserGameResource userGameResource) {
        userGameResourceMapper.updateUserGameResource(userGameResource);
    }

    @Override
    public void updateUserCoinByEmail(String userEmail, Integer userCoinChange) {
        userGameResourceMapper.updateUserCoinByEmail(userEmail, userCoinChange);
    }

    @Override
    public UserGameResource findByEmail(String userEmail) {
        return userGameResourceMapper.findByEmail(userEmail);
    }
}
