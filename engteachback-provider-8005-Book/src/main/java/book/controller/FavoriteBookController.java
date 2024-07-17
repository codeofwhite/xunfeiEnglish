package book.controller;

import book.entity.FavoriteBook;
import book.service.FavoriteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author codeofwhite
 * @date 2024/7/17
 * @Description
 */
@RestController
@RequestMapping("/favorites")
@CrossOrigin
public class FavoriteBookController {
    @Autowired
    private FavoriteBookService favoriteBookRepository;

    @PostMapping("/add")
    public FavoriteBook addToFavorites(@RequestBody FavoriteBook favoriteBook) {
        favoriteBook.setCreateTime(new Date());
        return favoriteBookRepository.save(favoriteBook);
    }

    @DeleteMapping("/remove")
    public void removeFromFavorites(@RequestParam Long bookId, @RequestParam String userEmail) {
        favoriteBookRepository.deleteByBookIdAndUserEmail(bookId, userEmail);
    }

    @GetMapping("/list")
    public List<FavoriteBook> getFavorites(@RequestParam String userEmail) {
        return favoriteBookRepository.findByUserEmail(userEmail);
    }
}
