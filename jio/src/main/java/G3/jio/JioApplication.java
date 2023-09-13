package G3.jio;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

import G3.jio.entities.Event;
import G3.jio.entities.User;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.UserRepository;

@SpringBootApplication
public class JioApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(JioApplication.class, args);

        EventRepository eventRepo = ctx.getBean(EventRepository.class);
        // System.out.println(
        //         eventRepo.save(new Event(1L, "Waikiki", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 2), "Sentosa",
        //                 200, true, 1, "Beach Day", true)));
        // UserRepository userRepo = ctx.getBean(UserRepository.class);
        // System.out.println(userRepo
        //         .save(new User(1L, "john weak", "john.weak@gmail.com", "123", "random/image", 'U', "01411234",
        //                 "98765432", LocalDate.of(2001, 1, 1))));
        // System.out.println(userRepo
        //         .save(new User(2L, "bobby bimbo", "bim.bo@gmail.com", "234", "random/image", 'U', "21124234",
        //                 "62226111", LocalDate.of(2001, 1, 2))));
    }
}
