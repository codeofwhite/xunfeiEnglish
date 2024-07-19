package engteach.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import engteach.user.entity.UserFavor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/19
 * @Description
 */
@Mapper
public interface UserFavorMapper {
    @Insert("INSERT INTO user_favor (user_email, user_favor) VALUES (#{user_email}, #{user_favor})")
    void insert(UserFavor userFavor);

    @Select("SELECT * FROM user_favor")
    List<UserFavor> selectList();
}
