package engteach.user.repository;

import engteach.user.entity.UserFavor;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/19
 * @Description
 */
public interface UserFavorService {
    void insertUserFavor(UserFavor userFavor);

    List<UserFavor> getAllUserFavors();
}
