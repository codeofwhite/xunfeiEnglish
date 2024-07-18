package engteach.api.day;


import com.alibaba.fastjson.JSONObject;
import engteach.api.day.util.Base64ImageUtil;
import engteach.api.day.util.MyUtil;
import okhttp3.HttpUrl;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

// 主函数入口
public class MainApplication {

    public static final String hostUrl = "https://spark-api.cn-huabei-1.xf-yun.com/v2.1/tti";
    public static final String appid = "30fa122e";
    public static final String apiSecret = "Mzg2ZDdjODJjYWM1MzE5M2ZkM2E0NGJl";
    public static final String apiKey = "96b41233a0974ee46f365d13ef6bea09";

    public static void main(String[] args) throws Exception {
        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
        // URL地址正确
        System.err.println(authUrl);
        String json = getBody(appid, "帮我画一个小鸟");
        // 发起Post请求
        System.err.println(json);
        String res = MyUtil.doPostJson(authUrl, null, json);
        System.out.println(res);

        // 解析JSON响应，提取Base64编码的图片字符串
        JSONObject jsonResponse = JSONObject.parseObject(res);
        String base64Image = jsonResponse.getJSONObject("payload")
                .getJSONObject("choices")
                .getJSONArray("text")
                .getJSONObject(0)
                .getString("content");
        Base64ImageUtil.saveBase64Image(base64Image, "output.png");
    }

    public static byte[] generateImage(String text) throws Exception {
        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
        // URL地址正确
        System.err.println(authUrl);
        String json = getBody(appid, text);
        // 发起Post请求
        System.err.println(json);
        String res = MyUtil.doPostJson(authUrl, null, json);
        System.out.println(res);

        // 解析JSON响应，提取Base64编码的图片字符串
        JSONObject jsonResponse = JSONObject.parseObject(res);
        String base64Image = jsonResponse.getJSONObject("payload")
                .getJSONObject("choices")
                .getJSONArray("text")
                .getJSONObject(0)
                .getString("content");
        return Base64ImageUtil.decodeBase64Image(base64Image);
    }


    // 鉴权方法
    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // date="Thu, 12 Oct 2023 03:05:28 GMT";
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" + "date: " + date + "\n" + "POST " + url.getPath() + " HTTP/1.1";
        // System.err.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // System.err.println(sha);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        // System.err.println(httpUrl.toString());
        return httpUrl.toString();
    }

    // 生成请求body体
    public static String getBody(String appid, String text) {
        return "{\n" +
                "  \"header\": {\n" +
                "    \"app_id\": \"" + appid + "\",\n" +
                "    \"uid\": \"123456789\"\n" +
                "  },\n" +
                "  \"parameter\": {\n" +
                "    \"chat\": {\n" +
                "      \"domain\": \"general\",\n" +
                "      \"temperature\": 0.5,\n" +
                "      \"max_tokens\": 4096\n" +
                "    }\n" +
                "  },\n" +
                "  \"payload\": {\n" +
                "    \"message\": {\n" +
                "      \"text\": [\n" +
                "        {\n" +
                "          \"role\": \"user\",\n" +
                "          \"content\": \"" + text + "\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }
}