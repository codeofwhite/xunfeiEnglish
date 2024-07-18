package wordBook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wordBook.entity.UserMastered;
import wordBook.entity.UserUnknown;
import wordBook.repository.UserMasteredRepository;
import wordBook.repository.UserUnknownRepository;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/18
 * @Description
 */
@Service
public class UserLearningService {


    @Autowired
    private UserUnknownRepository userUnknownRepository;

    @Autowired
    private UserMasteredRepository userMasteredRepository;

    public List<UserUnknown> getUnknownWords(String userEmail) {
        return userUnknownRepository.findByUserEmail(userEmail);
    }

    public List<UserMastered> getMasteredWords(String userEmail) {
        return userMasteredRepository.findByUserEmail(userEmail);
    }

    public int getUnknownWordCount(String userEmail) {
        return userUnknownRepository.findByUserEmail(userEmail).size();
    }

    public int getMasteredWordCount(String userEmail) {
        return userMasteredRepository.findByUserEmail(userEmail).size();
    }

    public double getLearningProgress(String userEmail) {
        int unknownCount = getUnknownWordCount(userEmail);
        int masteredCount = getMasteredWordCount(userEmail);
        int totalCount = unknownCount + masteredCount;
        return totalCount == 0 ? 0 : (double) masteredCount / totalCount * 100;
    }
}
