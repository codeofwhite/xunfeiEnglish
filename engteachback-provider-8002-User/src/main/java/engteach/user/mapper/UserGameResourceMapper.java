package engteach.user.mapper;

import engteach.user.entity.UserGameResource;
import org.apache.ibatis.annotations.*;

/**
 * @author codeofwhite
 * @date 2024/7/17
 * @Description
 */
@Mapper
public interface UserGameResourceMapper {

    UserGameResource getUserGameResourceByEmail(@Param("userEmail") String userEmail);

    void insertUserGameResource(UserGameResource userGameResource);

    void updateUserGameResource(UserGameResource userGameResource);
}
