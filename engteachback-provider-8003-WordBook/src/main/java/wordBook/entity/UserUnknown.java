package wordBook.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * (UserUnknown)实体类
 *
 * @author makejava
 * @since 2024-06-26 20:04:08
 */
@Getter
@Setter
public class UserUnknown implements Serializable {
    private static final long serialVersionUID = -65317508483277633L;

    private Integer id;

    private String userEmail;

    private String wordName;
}

