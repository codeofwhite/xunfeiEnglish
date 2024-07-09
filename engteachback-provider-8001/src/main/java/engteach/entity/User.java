package engteach.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author codeofwhite
 * @date 2024/6/26
 * @Description
 */
@Setter
@Getter
public class User {
    private static final long serialVersionUID = 146796128758839756L;

    private Integer id;

    private String userEmail;

    private String userName;

    private String userPassword;

    private Integer userSex;

    private Date createdTime;

    private Date updatedTime;

    private String userAvatar;
}
