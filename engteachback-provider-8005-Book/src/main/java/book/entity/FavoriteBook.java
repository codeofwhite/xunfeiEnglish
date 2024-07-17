package book.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author codeofwhite
 * @date 2024/7/17
 * @Description
 */
@Document(collection = "favorBook")
@Getter
@Setter
public class FavoriteBook {
    @Id
    private String id;
    private String bookId;
    private String userEmail;

    private Date createTime;
}
