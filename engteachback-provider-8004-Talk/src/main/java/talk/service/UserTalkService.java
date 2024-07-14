package talk.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import talk.entity.UserTalk;

public interface UserTalkService extends MongoRepository<UserTalk, String> {
}