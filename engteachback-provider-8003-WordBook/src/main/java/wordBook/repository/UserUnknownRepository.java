package wordBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wordBook.entity.UserUnknown;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/18
 * @Description
 */
public interface UserUnknownRepository extends JpaRepository<UserUnknown, Integer> {
    List<UserUnknown> findByUserEmail(String userEmail);
}
