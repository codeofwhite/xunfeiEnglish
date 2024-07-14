package book.service;

import book.entity.BookTalk;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author codeofwhite
 * @date 2024/7/14
 * @Description
 */
public interface BookTalkService extends MongoRepository<BookTalk, String> {
}
