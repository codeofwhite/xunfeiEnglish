package engteach.controller;


// 导入相关类

import engteach.api.iflytek.MachineTranslationMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author codeofwhite
 * @date 2024/7/9
 * @Description
 */
@RestController
@CrossOrigin
@RequestMapping("xunfei")
public class xunfeiController {

    @Autowired
    private MachineTranslationMain translationService;

    @PostMapping("/translate")
    public ResponseEntity<String> translate(@RequestBody String textToTranslate) {
        try {
            String translatedText = translationService.translateText(textToTranslate);
            return ResponseEntity.ok().body(translatedText);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
        }
    }
}
