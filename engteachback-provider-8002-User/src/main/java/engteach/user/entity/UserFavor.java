package engteach.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * (UserFavor)实体类
 *
 * @author makejava
 * @since 2024-07-19 22:03:01
 */
@Getter
@Setter
@TableName(value = "user_favor")
public class UserFavor implements Serializable {
    private static final long serialVersionUID = 815343083372448576L;

    private Integer id;

    private String user_email;

    private String user_favor;

}

