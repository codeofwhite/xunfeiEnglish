package wordBook.repository.Impl;

import wordBook.mapper.WordBookMapper;
import wordBook.repository.WordBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/6/26
 * @Description
 */
@Repository
public class WordBookRepositoryImpl implements WordBookRepository {
    @Autowired
    WordBookMapper wordBookMapper;

    @Override
    public void insertUnknown(String user_email, String word_name) {
        if (wordBookMapper.countUnknown(user_email, word_name) == 0) {
            wordBookMapper.insertUnknown(user_email, word_name);
        }
    }

    @Override
    public List<String> selectUnknown(String user_email) {
        return wordBookMapper.selectUnknown(user_email);
    }

    @Override
    public void removeUnknown(String userEmail, String wordName) {
        wordBookMapper.removeUnknown(userEmail, wordName);
    }

    @Override
    public void insertMastered(String user_email, String word_name) {
        if (wordBookMapper.countMastered(user_email, word_name) == 0) {
            wordBookMapper.insertMastered(user_email, word_name);
        }
    }

    @Override
    public List<String> selectMastered(String user_email) {
        return wordBookMapper.selectMastered(user_email);
    }
}
