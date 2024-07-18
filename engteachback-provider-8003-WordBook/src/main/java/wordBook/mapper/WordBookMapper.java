package wordBook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/6/28
 * @Description
 */
@Mapper
public interface WordBookMapper {
    public void insertUnknown(@Param("user_email") String user_email, @Param("word_name") String word_name);

    public List<String> selectUnknown(@Param("user_email") String user_email);

    public void removeUnknown(@Param("userEmail") String userEmail, @Param("wordName") String wordName);

    public void insertMastered(@Param("user_email") String user_email, @Param("word_name") String word_name);

    public List<String> selectMastered(@Param("user_email") String user_email);

    int countMastered(@Param("user_email") String user_email, @Param("word_name") String word_name);

    int countUnknown(@Param("user_email") String user_email, @Param("word_name") String word_name);
}
