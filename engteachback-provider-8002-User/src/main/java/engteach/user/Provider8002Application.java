package engteach.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author codeofwhite
 * @date 2024/7/12
 * @Description
 */
@SpringBootApplication
@MapperScan("engteach.user.mapper")
public class Provider8002Application {
    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(Provider8002Application.class, args);
    }
}
