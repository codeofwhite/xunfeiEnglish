package engteach.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author codeofwhite
 * @date 2024/6/26
 * @Description
 */
@Setter
@Getter
@TableName(value = "user_info")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 146796128758839756L;

    private Integer id;

    private String user_email;

    private String user_name;

    private String user_password;

    private Integer user_sex;

    private Date created_time;

    private Date updated_time;

    private String user_avatar;
}
