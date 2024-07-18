package book.controller;

import book.entity.BookTalk;
import book.entity.FavoriteBook;
import book.service.BookStaticService;
import book.service.BookTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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

    @Autowired
    BookStaticService bookStaticService;

    @PostMapping
    public BookTalk createTalk(@RequestBody BookTalk bookTalk) {
        bookTalk.setBookTalkId(UUID.randomUUID().toString());
        bookTalk.setCreateTime(new Date());
        return bookTalkService.save(bookTalk);
    }

    @GetMapping("/averageAccuracy/{userEmail}")
    public double getAverageAccuracy(@PathVariable String userEmail) {
        return bookStaticService.calculateAverageAccuracy(userEmail);
    }

    @GetMapping("/averageFluency/{userEmail}")
    public double getAverageFluency(@PathVariable String userEmail) {
        return bookStaticService.calculateAverageFluency(userEmail);
    }

    @GetMapping("/averageIntegrity/{userEmail}")
    public double getAverageIntegrity(@PathVariable String userEmail) {
        return bookStaticService.calculateAverageIntegrity(userEmail);
    }

    @GetMapping("/accuracyTrend/{userEmail}")
    public List<Double> getAccuracyTrend(@PathVariable String userEmail) {
        return bookStaticService.calculateAccuracyTrend(userEmail);
    }
}
