package engteach.user.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author codeofwhite
 * @date 2024/6/26
 * @Description
 */
@Setter
@Getter
public class LoginResponse {
    private String userEmail;
    private boolean success;

    public LoginResponse(String userEmail, boolean success) {
        this.userEmail = userEmail;
        this.success = success;
    }
}
