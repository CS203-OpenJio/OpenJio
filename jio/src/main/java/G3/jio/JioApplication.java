package G3.jio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@PropertySource("classpath:.env")
public class JioApplication {

        public static void main(String[] args) {

                SpringApplication.run(JioApplication.class, args);
        }
}
