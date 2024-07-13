package engteach.user.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author codeofwhite
 * @date 2024/7/3
 * @Description
 */
@Setter
@Getter
public class RegisterRequest {
    private String userEmail;
    private String userName;
    private String userPassword;
}
