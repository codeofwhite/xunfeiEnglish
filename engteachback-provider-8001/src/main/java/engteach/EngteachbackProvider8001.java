package engteach;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author codeofwhite
 * @date 2024/6/26
 * @Description
 */
@SpringBootApplication
@MapperScan("engteach.mapper")
public class EngteachbackProvider8001 {
    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(EngteachbackProvider8001.class, args);
    }
}
