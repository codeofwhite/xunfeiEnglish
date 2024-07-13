package wordBook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author codeofwhite
 * @date 2024/7/13
 * @Description
 */
@SpringBootApplication
@MapperScan("wordBook.mapper")
public class Provider8003Application {
    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(Provider8003Application.class, args);
    }
}
