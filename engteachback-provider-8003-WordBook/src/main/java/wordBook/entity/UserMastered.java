package wordBook.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * (UserMastered)实体类
 *
 * @author makejava
 * @since 2024-07-17 10:07:25
 */
@Getter
@Setter
public class UserMastered implements Serializable {
    @Serial
    private static final long serialVersionUID = 737410336665770222L;

    private Integer id;

    private String userEmail;

    private String wordName;


}

