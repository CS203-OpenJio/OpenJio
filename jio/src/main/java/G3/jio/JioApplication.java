package G3.jio;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import G3.jio.config.Role;
import G3.jio.entities.Student;
import G3.jio.repositories.StudentRepository;

@SpringBootApplication
public class JioApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(JioApplication.class, args);

        // JPA user repository init
        StudentRepository studentRepository = ctx.getBean(StudentRepository.class);
        BCryptPasswordEncoder encoder = ctx.getBean(BCryptPasswordEncoder.class);
        System.out.println("[Add Student]: "
                + studentRepository.save(
                        new Student("testname", "admin@admin.com", encoder.encode("admin"), "12345678", "87654321",
                                LocalDate.of(2023, 1, 2), Role.ADMIN))
                        .getUsername());

        // EventRepository eventRepo = ctx.getBean(EventRepository.class);
        // System.out.println(
        // eventRepo.save(new Event(1L, "Waikiki", LocalDate.of(2023, 1, 1),
        // LocalDate.of(2023, 1, 2), "Sentosa",
        // 200, true, 1, "Beach Day", true)));
        // UserRepository userRepo = ctx.getBean(UserRepository.class);
        // System.out.println(userRepo
        // .save(new User(1L, "john weak", "john.weak@gmail.com", "123", "random/image",
        // 'U', "01411234",
        // "98765432", LocalDate.of(2001, 1, 1))));
        // System.out.println(userRepo
        // .save(new User(2L, "bobby bimbo", "bim.bo@gmail.com", "234", "random/image",
        // 'U', "21124234",
        // "62226111", LocalDate.of(2001, 1, 2))));
    }
}
