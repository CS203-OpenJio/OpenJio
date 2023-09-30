package G3.jio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JioApplication {

        public static void main(String[] args) {

                ApplicationContext ctx = SpringApplication.run(JioApplication.class, args);
        }
}
