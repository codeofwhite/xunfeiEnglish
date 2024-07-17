package engteach.user.repository;

import engteach.user.entity.UserGameResource;

/**
 * @author codeofwhite
 * @date 2024/7/17
 * @Description
 */
public interface UserGameResourceRepository {
    UserGameResource getUserGameResourceByEmail(String userEmail);

    void insertUserGameResource(UserGameResource userGameResource);

    void updateUserGameResource(UserGameResource userGameResource);

    void updateUserCoinByEmail(String userEmail, Integer userCoinChange);

    UserGameResource findByEmail(String userEmail);
}
