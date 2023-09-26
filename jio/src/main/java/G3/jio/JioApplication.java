package G3.jio;

import java.time.LocalDate;
import java.util.HashSet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import G3.jio.config.Role;
import G3.jio.entities.Event;
import G3.jio.entities.Student;
import G3.jio.repositories.EventRepository;
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
                EventRepository eventRepo = ctx.getBean(EventRepository.class);

                
        eventRepo.save(new Event(null, "CS1337", LocalDate.of(2023, 1, 2), LocalDate.of(2023, 1, 2), "SCIS-1", "placeholder", 128, 1, "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Why do we use it? It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like). Where does it come from? Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32. The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.", true, null));
        eventRepo.save(new Event(null, ".Hack Social Night", LocalDate.of(2023, 2, 10), LocalDate.of(2023, 2, 10), "SCIS1 B1 Alcove", "/socialnight.jpg", 55, 0, "Hi All! It is with great pleasure that we invite you to .Hack’s inaugural Social Night 2023!🥳 Social Night will serve as a networking platform for you to gain insightful knowledge through the distinguished speakers from companies such as Credit Suisse. You will also get the chance to interact with the speakers and other .Hack members during the event itself. .Hack hopes to provide you with the best experiences, to better prepare you for the technology industry in the workforce. As such, we sincerely hope that you will consider attending Social Night 2023 with us!", true, null));
        eventRepo.save(new Event(null, "Ellipsis Back2Sku Welfare Drive", LocalDate.of(2023, 8, 16), LocalDate.of(2023, 8, 16), "SCIS1 Basement (near Big Steps)", "/back2skuwelfaredrive.jpg", 150, 0, "Got the back-to-school blues? 🎒💔 Well, you don’t have to dwell on it – because Ellipsis is ready to banish them with our upcoming Back2Sku Welfare Drive! We have packaged snacks 🧸 and drinks 🧃for you, as well as chocolate ice cream 🍨 and customised Taiwanese dessert bowls 🥣, so make sure you don’t miss out!", true, null));
        eventRepo.save(new Event(null, ".Hack WAD2 Workshop 2023", LocalDate.of(2023, 10, 20), LocalDate.of(2023, 10, 20), "SCIS1 Seminar Room B1-1", "/wad2workshop.jpg", 80, 0, "Are you taking WAD2 (Web App Development 2) this year? Need a crash course on how to deploy your project?😉 Don't sweat it! .Hack have put together a WAD2 Workshop masterclass! It aims to help students get a better understanding of AWS services and how to make use of them for your project. We will be going through AWS using S3 and EC2, CI/CD tools and demonstrating a realistic project deployed onto a live website.", true, null));
        eventRepo.save(new Event(null, ".Hack Certification Programme (DHCP)", LocalDate.of(2023, 3, 17), LocalDate.of(2023, 3, 17), "SCIS2 Computing Lab B1-1", "/dhcpworkshop.jpg", 90, 0, "Thinking of picking up a new tech-related skill? Or looking for something that will boost your portfolio? Look no further, .Hack’s much-anticipated DHCP is back this year! By participating in the DHCP, you will be able to take the coveted AWS’s Cloud Practitioner Certification, under the guidance of our experienced .Hack mentors!", true, null));

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
