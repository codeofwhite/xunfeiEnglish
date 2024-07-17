package engteach.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Date;
import java.io.Serializable;

/**
 * (UserGameResource)实体类
 *
 * @author makejava
 * @since 2024-07-17 11:45:52
 */
@Getter
@Setter
@TableName(value = "user_game_resource")
public class UserGameResource implements Serializable {
    @Serial
    private static final long serialVersionUID = -48104131759770154L;

    private Integer id;

    private String user_email;

    private Integer user_coin;

    private Integer user_spark;

    private Date create_time;

    private Date update_time;

    private Integer user_level;
}

