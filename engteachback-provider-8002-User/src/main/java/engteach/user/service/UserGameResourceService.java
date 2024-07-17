package engteach.user.service;

import engteach.user.entity.UserGameResource;
import engteach.user.repository.UserGameResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author codeofwhite
 * @date 2024/7/18
 * @Description
 */
@Service
public class UserGameResourceService {

    @Autowired
    private UserGameResourceRepository userGameResourceRepository;

    public String updateUserCoin(String userEmail, Integer userCoinChange) {
        UserGameResource userGameResource = userGameResourceRepository.findByEmail(userEmail);
        if (userGameResource != null) {
            int newCoinBalance = userGameResource.getUser_coin() + userCoinChange;
            if (newCoinBalance < 0) {
                return "余额不足";
            } else {
                userGameResourceRepository.updateUserCoinByEmail(userEmail, userCoinChange);
                return "金币更新成功";
            }
        } else {
            return "用户不存在";
        }
    }
}
