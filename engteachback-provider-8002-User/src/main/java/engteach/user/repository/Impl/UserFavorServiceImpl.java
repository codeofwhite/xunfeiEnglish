package engteach.user.repository.Impl;

import engteach.user.entity.UserFavor;
import engteach.user.mapper.UserFavorMapper;
import engteach.user.repository.UserFavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/19
 * @Description
 */
@Service
public class UserFavorServiceImpl implements UserFavorService {

    @Autowired
    private UserFavorMapper userFavorMapper;

    @Override
    public void insertUserFavor(UserFavor userFavor) {
        userFavorMapper.insert(userFavor);
    }

    @Override
    public List<UserFavor> getAllUserFavors() {
        return userFavorMapper.selectList();
    }
}
