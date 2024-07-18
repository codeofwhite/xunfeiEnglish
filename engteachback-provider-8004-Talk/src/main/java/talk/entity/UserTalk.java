package talk.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

import java.util.Date;
import java.util.List;

@Document(collection = "userTalk")
@Getter
@Setter
public class UserTalk {
    @Id
    private String id;
    private String userEmail;
    private List<Double> accuracy;
    private List<Double> fluency;
    private List<Double> integrity;
    private Double totalScore;

    private String talkId;

    private Date createTime;

    private String talkType; // 新增字段，记录对话类型（例如，语音对话）
    // Getters and setters
}