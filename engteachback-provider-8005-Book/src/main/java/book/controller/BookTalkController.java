package book.controller;

import book.entity.BookTalk;
import book.service.BookTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author codeofwhite
 * @date 2024/7/14
 * @Description
 */
@RestController
@RequestMapping("/api/bookTalk")
@CrossOrigin
public class BookTalkController {

    @Autowired
    BookTalkService bookTalkService;

    @PostMapping
    public BookTalk createTalk(@RequestBody BookTalk bookTalk) {
        bookTalk.setBookTalkId(UUID.randomUUID().toString());
        bookTalk.setCreateTime(new Date());
        return bookTalkService.save(bookTalk);
    }
}
