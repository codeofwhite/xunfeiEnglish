package engteach.repository.impl;

import engteach.mapper.WordBookMapper;
import engteach.repository.WordBookRepository;
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
        wordBookMapper.insertUnknown(user_email, word_name);
    }

    @Override
    public List<String> selectUnknown(String user_email) {
        return wordBookMapper.selectUnknown(user_email);
    }
}
