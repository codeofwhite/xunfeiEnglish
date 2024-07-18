package engteach.controller;


// 导入相关类

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import engteach.api.iflytek.IseDemo;
import engteach.api.iflytek.MachineTranslationMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import engteach.api.day.MainApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private IseDemo iseDemo;

    @PostMapping("/translate")
    public ResponseEntity<String> translate(@RequestBody String textToTranslate) {
        try {
            String translatedText = translationService.translateText(textToTranslate);
            return ResponseEntity.ok().body(translatedText);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
        }
    }

    @PostMapping(value = "/score", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getScore(@RequestPart("audioFile") MultipartFile audioFile, @RequestParam("text") String text) {
        try {
            iseDemo.scoreSpeech(audioFile, text);
            String result = iseDemo.getResult();
            System.out.println("Result: " + result); // 确保这里打印出正确的结果

            // 将结果解析为JSON格式
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            Map<String, String> resultMap = new HashMap<>();
            jsonObject.entrySet().forEach(entry -> resultMap.put(entry.getKey(), entry.getValue().getAsString()));

            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }

    @PostMapping(value = "/generateImage", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateImage(@RequestBody Map<String, String> request) {
        try {
            String text = request.get("text");
            byte[] imageBytes = MainApplication.generateImage(text);
            return ResponseEntity.ok().body(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
