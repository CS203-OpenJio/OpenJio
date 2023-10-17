package G3.jio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@PropertySource("classpath:.env")
public class JioApplication {

        public static void main(String[] args) {

                ApplicationContext ctx = SpringApplication.run(JioApplication.class, args);
        }
}
