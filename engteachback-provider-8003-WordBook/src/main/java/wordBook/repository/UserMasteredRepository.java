package wordBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wordBook.entity.UserMastered;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/18
 * @Description
 */
public interface UserMasteredRepository extends JpaRepository<UserMastered, Integer> {
    List<UserMastered> findByUserEmail(String userEmail);
}
