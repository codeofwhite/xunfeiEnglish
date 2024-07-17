package book.service;

import book.entity.FavoriteBook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/17
 * @Description
 */
public interface FavoriteBookService extends MongoRepository<FavoriteBook, String> {
    List<FavoriteBook> findByUserEmail(String userEmail);

    void deleteByBookIdAndUserEmail(Long bookId, String userEmail);
}
