package engteach.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * (UserAchievement)实体类
 *
 * @author makejava
 * @since 2024-07-18 23:25:45
 */
@Getter
@Setter
@TableName(value = "user_achievement")
public class UserAchievement implements Serializable {
    private static final long serialVersionUID = -36853565563092471L;

    private Integer id;

    private String user_email;

    private String user_achievement;

    private Date create_date;

    private Date update_date;

}

