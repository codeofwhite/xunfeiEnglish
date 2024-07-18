package engteach.api.day.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @author codeofwhite
 * @date 2024/7/18
 * @Description
 */
public class Base64ImageUtil {
    public static void saveBase64Image(String base64String, String filePath) {
        // 解码Base64字符串
        byte[] imageBytes = Base64.getDecoder().decode(base64String);

        // 将字节数组写入文件
        try (OutputStream out = new FileOutputStream(filePath)) {
            out.write(imageBytes);
            System.out.println("图片保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 作为接口的传送
    public static byte[] decodeBase64Image(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }
}