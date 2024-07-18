package wordBook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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
@Entity
@Table(name = "user_mastered")
public class UserMastered implements Serializable {
    @Serial
    private static final long serialVersionUID = 737410336665770222L;

    @Setter
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private String wordName;

}

