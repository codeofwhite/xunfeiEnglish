package wordBook.controller;

import wordBook.repository.WordBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/6/26
 * @Description
 */
@RestController
@RequestMapping("/word")
@CrossOrigin
public class WordBookController {

    @Autowired
    WordBookRepository wordBookService;

    @PostMapping("/insertUnknown")
    public String insertUnknown(@RequestParam("user_email") String user_email,
                                @RequestParam("word_name") String word_name) {
        wordBookService.insertUnknown(user_email, word_name);
        return "success";
    }

    @PostMapping("/selectUnknown")
    public List<String> selectUnknown(@RequestParam("user_email") String user_email) {
        List<String> list = wordBookService.selectUnknown(user_email);
        return list;
    }

    @PostMapping("/removeUnknown")
    public String removeUnknown(@RequestParam("user_email") String user_email,
                                @RequestParam("word_name") String word_name) {
        wordBookService.removeUnknown(user_email, word_name);
        return "success";
    }

    @PostMapping("/insertMastered")
    public String insertMaster(@RequestParam("user_email") String user_email,
                               @RequestParam("word_name") String word_name) {
        wordBookService.insertMastered(user_email, word_name);
        return "success";
    }

    @PostMapping("/selectMastered")
    public List<String> selectMaster(@RequestParam("user_email") String user_email) {
        List<String> list = wordBookService.selectMastered(user_email);
        return list;
    }
}
