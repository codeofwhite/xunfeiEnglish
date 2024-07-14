package book.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/14
 * @Description
 */
@Document(collection = "bookTalk")
@Getter
@Setter
public class BookTalk {
    @Id
    private String id;
    private String userEmail;
    private List<Double> accuracy;
    private List<Double> fluency;
    private List<Double> integrity;
    private Double totalScore;

    private String bookTalkId;

    private Date createTime;
}
