package wordBook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * (UserUnknown)实体类
 *
 * @author makejava
 * @since 2024-06-26 20:04:08
 */
@Getter
@Setter
@Entity
@Table(name = "user_unknown")
public class UserUnknown implements Serializable {
    private static final long serialVersionUID = -65317508483277633L;

    @Setter
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private String wordName;
}

