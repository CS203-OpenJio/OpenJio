package G3.jio.spring;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import G3.jio.entities.Event;
import G3.jio.entities.Organiser;
import G3.jio.entities.Role;
import G3.jio.entities.Student;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private OrganiserRepository organiserRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial users
        createStudentIfNotFound("admin", "admin@admin.com", "admin", Role.ADMIN);
        createOrganiserIfNotFound("organiser", "org@org.com", "organiser", Role.ORGANISER);
        createStudentIfNotFound("student", "student@student.com", "student", Role.STUDENT);

        // == create events
        createEventIfNotFound(".Hack Social Night", LocalDateTime.of(2023, 10, 1, 7, 0),
            LocalDateTime.of(2023, 10, 1, 19, 0), "SCIS1 B1 Alcove",
            "/socialnight.jpg", 55, 0,
            "Hi All! It is with great pleasure that we invite you to .Hack’s inaugural Social Night 2023!🥳 Social Night will serve as a networking platform for you to gain insightful knowledge through the distinguished speakers from companies such as Credit Suisse. You will also get the chance to interact with the speakers and other .Hack members during the event itself. .Hack hopes to provide you with the best experiences, to better prepare you for the technology industry in the workforce. As such, we sincerely hope that you will consider attending Social Night 2023 with us!",
            true, 0);

        createEventIfNotFound("Ellipsis Back2Sku Welfare Drive", LocalDateTime.of(2023, 10, 2, 7, 0),
            LocalDateTime.of(2023, 10, 2, 19,0), "SCIS1 Basement (near Big Steps)",
            "/back2skuwelfaredrive.jpg", 150, 0,
            "Got the back-to-school blues? 🎒💔 Well, you don’t have to dwell on it – because Ellipsis is ready to banish them with our upcoming Back2Sku Welfare Drive! We have packaged snacks 🧸 and drinks 🧃for you, as well as chocolate ice cream 🍨 and customised Taiwanese dessert bowls 🥣, so make sure you don’t miss out!",
            true, 0);

        createEventIfNotFound(".Hack WAD2 Workshop 2023", LocalDateTime.of(2023, 10, 3, 7, 0),
            LocalDateTime.of(2023, 10, 3, 19, 0), "SCIS1 Seminar Room B1-1", "/wad2workshop.jpg", 80, 0,
            "Are you taking WAD2 (Web App Development 2) this year? Need a crash course on how to deploy your project?😉 Don't sweat it! .Hack have put together a WAD2 Workshop masterclass! It aims to help students get a better understanding of AWS services and how to make use of them for your project. We will be going through AWS using S3 and EC2, CI/CD tools and demonstrating a realistic project deployed onto a live website.",
            true, 0);

        createEventIfNotFound(".Hack Certification Programme (DHCP)", LocalDateTime.of(2023, 10, 4, 7, 0),
            LocalDateTime.of(2023, 10, 4, 19, 0), "SCIS2 Computing Lab B1-1", "/dhcpworkshop.jpg", 90, 0,
            "Thinking of picking up a new tech-related skill? Or looking for something that will boost your portfolio? Look no further, .Hack’s much-anticipated DHCP is back this year! By participating in the DHCP, you will be able to take the coveted AWS’s Cloud Practitioner Certification, under the guidance of our experienced .Hack mentors!",
            true, 0);

        alreadySetup = true;
    }

    @Transactional
    public Student createStudentIfNotFound(String name, String email, String password, Role role) {

        Student student = studentRepository.findByEmail(email).map(s -> {
            return s;
        }).orElse(null);

        if (student == null) {
            student = new Student();
            student.setName(name);
            student.setEmail(email);
            student.setPassword(passwordEncoder.encode(password));
            student.setRole(role);
        }

        student = studentRepository.save(student);
        return student;
    }

    @Transactional
    public Organiser createOrganiserIfNotFound(String name, String email, String password, Role role) {

        Organiser organiser = organiserRepository.findByEmail(email).map(s -> {
            return s;
        }).orElse(null);

        if (organiser == null) {
            organiser = new Organiser();
            organiser.setName(name);
            organiser.setEmail(email);
            organiser.setPassword(passwordEncoder.encode(password));
            organiser.setRole(role);
        }

        organiser = organiserRepository.save(organiser);
        return organiser;
    }

    @Transactional
    public Event createEventIfNotFound(String name, LocalDateTime startDateTime, LocalDateTime endDateTime,
            String venue, String image, int capacity, int algo, String description,
            boolean isVisible, int minScore) {

        Event event = eventRepository.findByNameAndStartDateTime(name, startDateTime);

        if (event == null) {
            event = new Event();
            event.setName(name);
            event.setStartDateTime(startDateTime);
            event.setEndDateTime(endDateTime);
            event.setVenue(venue);
            event.setImage(image);
            event.setCapacity(capacity);
            event.setAlgo(algo);
            event.setDescription(description);
            event.setVisible(isVisible);
            event.setMinScore(minScore);
        }

        event = eventRepository.save(event);
        return event;
    }
}
