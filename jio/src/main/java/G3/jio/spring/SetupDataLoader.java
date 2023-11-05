package G3.jio.spring;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Organiser;
import G3.jio.entities.Role;
import G3.jio.entities.Student;
import G3.jio.repositories.EventRegistrationRepository;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final StudentRepository studentRepository;

    private final OrganiserRepository organiserRepository;

    private final EventRepository eventRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final EventRegistrationRepository eventRegistrationRepository;

    // API

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial users
        createStudentIfNotFound("admin", "admin@admin.com", "admin", Role.ADMIN);
        createOrganiserIfNotFound("organiser", "org@org.com", "organiser", Role.ORGANISER);
        createStudentIfNotFound("0student", "0student@student.com", "student", Role.STUDENT);
        createStudentIfNotFound("25student", "25student@student.com", "student", Role.STUDENT);
        createStudentIfNotFound("50student", "50student@student.com", "student", Role.STUDENT);
        createStudentIfNotFound("75student", "75student@student.com", "student", Role.STUDENT);
        createStudentIfNotFound("100student", "100student@student.com", "student", Role.STUDENT);
        createStudentIfNotFound("student7", "student7@student.com", "student", Role.STUDENT);
        createStudentIfNotFound("student8", "student8@student.com", "student", Role.STUDENT);
        createStudentIfNotFound("student9", "student9@student.com", "student", Role.STUDENT);
        createStudentIfNotFound("student10", "student10@student.com", "student", Role.STUDENT);

        // == create events
        createCompletedEventIfNotFound(".Hack Social Night-dummy", LocalDateTime.of(2023, 10, 1, 7, 0),
                LocalDateTime.of(2023, 10, 1, 19, 0), "SCIS1 B1 Alcove",
                "/socialnight.jpg", 5, "FCFS",
                "Hi All! It is with great pleasure that we invite you to .Hack’s inaugural Social Night 2023! Social Night will serve as a networking platform for you to gain insightful knowledge through the distinguished speakers from companies such as Credit Suisse. You will also get the chance to interact with the speakers and other .Hack members during the event itself. .Hack hopes to provide you with the best experiences, to better prepare you for the technology industry in the workforce. As such, we sincerely hope that you will consider attending Social Night 2023 with us!",
             0);

        createCompletedEventIfNotFound("Ellipsis Back2Sku Welfare Drive-dummy", LocalDateTime.of(2023, 10, 2, 7, 0),
                LocalDateTime.of(2023, 10, 2, 19, 0), "SCIS1 Basement (near Big Steps)",
                "/back2skuwelfaredrive.jpg", 5, "FCFS",
                "Got the back-to-school blues? Well, you don’t have to dwell on it – because Ellipsis is ready to banish them with our upcoming Back2Sku Welfare Drive! We have packaged snacks and drinks for you, as well as chocolate ice cream and customised Taiwanese dessert bowls, so make sure you don’t miss out!",
             0);

        createCompletedEventIfNotFound(".Hack WAD2 Workshop 2023-dummy", LocalDateTime.of(2023, 10, 3, 7, 0),
                LocalDateTime.of(2023, 10, 3, 19, 0), "SCIS1 Seminar Room B1-1", "/wad2workshop.jpg", 5, "FCFS",
                "Are you taking WAD2 (Web App Development 2) this year? Need a crash course on how to deploy your project? Don't sweat it! .Hack have put together a WAD2 Workshop masterclass! It aims to help students get a better understanding of AWS services and how to make use of them for your project. We will be going through AWS using S3 and EC2, CI/CD tools and demonstrating a realistic project deployed onto a live website.",
             0);

        createCompletedEventIfNotFound(".Hack Certification Programme (DHCP)-dummy", LocalDateTime.of(2023, 10, 4, 7, 0),
                LocalDateTime.of(2023, 10, 4, 19, 0), "SCIS2 Computing Lab B1-1", "/dhcpworkshop.jpg", 5, "FCFS",
                "Thinking of picking up a new tech-related skill? Or looking for something that will boost your portfolio? Look no further, .Hack’s much-anticipated DHCP is back this year! By participating in the DHCP, you will be able to take the coveted AWS’s Cloud Practitioner Certification, under the guidance of our experienced .Hack mentors!",
             0);
                
        // Actual
        createEventIfNotFound(".Hack Social Night", LocalDateTime.of(2023, 10, 1, 7, 0),
                LocalDateTime.of(2023, 10, 1, 19, 0), "SCIS1 B1 Alcove",
                "/socialnight.jpg", 3, "FCFS",
                "Hi All! It is with great pleasure that we invite you to .Hack’s inaugural Social Night 2023! Social Night will serve as a networking platform for you to gain insightful knowledge through the distinguished speakers from companies such as Credit Suisse. You will also get the chance to interact with the speakers and other .Hack members during the event itself. .Hack hopes to provide you with the best experiences, to better prepare you for the technology industry in the workforce. As such, we sincerely hope that you will consider attending Social Night 2023 with us!",
                true, 0);

        createEventIfNotFound("Ellipsis Back2Sku Welfare Drive", LocalDateTime.of(2023, 10, 2, 7, 0),
                LocalDateTime.of(2023, 10, 2, 19, 0), "SCIS1 Basement (near Big Steps)",
                "/back2skuwelfaredrive.jpg", 5, "FCFS",
                "Got the back-to-school blues? Well, you don’t have to dwell on it – because Ellipsis is ready to banish them with our upcoming Back2Sku Welfare Drive! We have packaged snacks and drinks for you, as well as chocolate ice cream and customised Taiwanese dessert bowls, so make sure you don’t miss out!",
                true, 0);

        createEventIfNotFound(".Hack WAD2 Workshop 2023", LocalDateTime.of(2023, 10, 3, 7, 0),
                LocalDateTime.of(2023, 10, 3, 19, 0), "SCIS1 Seminar Room B1-1", "/wad2workshop.jpg", 10, "FCFS",
                "Are you taking WAD2 (Web App Development 2) this year? Need a crash course on how to deploy your project? Don't sweat it! .Hack have put together a WAD2 Workshop masterclass! It aims to help students get a better understanding of AWS services and how to make use of them for your project. We will be going through AWS using S3 and EC2, CI/CD tools and demonstrating a realistic project deployed onto a live website.",
                true, 0);

        createEventIfNotFound(".Hack Certification Programme (DHCP)", LocalDateTime.of(2023, 10, 4, 7, 0),
                LocalDateTime.of(2023, 10, 4, 19, 0), "SCIS2 Computing Lab B1-1", "/dhcpworkshop.jpg", 15, "FCFS",
                "Thinking of picking up a new tech-related skill? Or looking for something that will boost your portfolio? Look no further, .Hack’s much-anticipated DHCP is back this year! By participating in the DHCP, you will be able to take the coveted AWS’s Cloud Practitioner Certification, under the guidance of our experienced .Hack mentors!",
                true, 0);


        // create registrations
        createCompletedEventRegistrationIfNotFound(2L, 1L, false);
        createCompletedEventRegistrationIfNotFound(2L, 2L, false);
        createCompletedEventRegistrationIfNotFound(2L, 3L, false);
        createCompletedEventRegistrationIfNotFound(2L, 4L, false);

        createCompletedEventRegistrationIfNotFound(3L, 1L, true);
        createCompletedEventRegistrationIfNotFound(3L, 2L, false);
        createCompletedEventRegistrationIfNotFound(3L, 3L, false);
        createCompletedEventRegistrationIfNotFound(3L, 4L, false);

        createCompletedEventRegistrationIfNotFound(4L, 1L, true);
        createCompletedEventRegistrationIfNotFound(4L, 2L, true);
        createCompletedEventRegistrationIfNotFound(4L, 3L, false);
        createCompletedEventRegistrationIfNotFound(4L, 4L, false);

        createCompletedEventRegistrationIfNotFound(5L, 1L, true);
        createCompletedEventRegistrationIfNotFound(5L, 2L, true);
        createCompletedEventRegistrationIfNotFound(5L, 3L, true);
        createCompletedEventRegistrationIfNotFound(5L, 4L, false);

        createCompletedEventRegistrationIfNotFound(6L, 1L, true);
        createCompletedEventRegistrationIfNotFound(6L, 2L, true);
        createCompletedEventRegistrationIfNotFound(6L, 3L, true);
        createCompletedEventRegistrationIfNotFound(6L, 4L, true);

        alreadySetup = true;
    }

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

        student = studentRepository.saveAndFlush(student);
        return student;
    }

    public void createEventRegistrationIfNotFound(Long studentId, Long eventId, boolean present) {

        if(eventRegistrationRepository.existsByStudentIdAndEventId(studentId, eventId)) {
            return;
        }

        EventRegistration er = new EventRegistration();
        er.setStudent(studentRepository.findById(studentId).get());
        er.setEvent(eventRepository.findById(eventId).get());
        er.setPresentForEvent(present);

        studentRepository.findById(studentId).get().addEventRegistration(er);

        eventRepository.findById(eventId).get().addEventRegistration(er);

        eventRegistrationRepository.saveAndFlush(er);
    }

    public void createCompletedEventRegistrationIfNotFound(Long studentId, Long eventId, boolean present) {

        if(eventRegistrationRepository.existsByStudentIdAndEventId(studentId, eventId)) {
            return;
        }

        EventRegistration er = new EventRegistration();
        er.setStudent(studentRepository.findById(studentId).get());
        er.setEvent(eventRepository.findById(eventId).get());
        er.setPresentForEvent(present);
        er.setCompleted(true);
        er.setTimeCompleted(LocalDateTime.now());

        studentRepository.findById(studentId).get().addEventRegistration(er);

        eventRepository.findById(eventId).get().addEventRegistration(er);

        eventRegistrationRepository.saveAndFlush(er);
    }

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

        organiser = organiserRepository.saveAndFlush(organiser);
        return organiser;
    }

    public Event createEventIfNotFound(String name, LocalDateTime startDateTime, LocalDateTime endDateTime,
            String venue, String image, int capacity, String algo, String description,
            boolean isVisible, int minScore) {

        Event event = eventRepository.findByNameAndStartDateTime(name, startDateTime).map(e -> {return e;}).orElse(null);

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
            event.setOrganiser(organiserRepository.findById(1L).get());
        }

        event = eventRepository.saveAndFlush(event);
        return event;
    }

    public Event createCompletedEventIfNotFound(String name, LocalDateTime startDateTime, LocalDateTime endDateTime,
            String venue, String image, int capacity, String algo, String description, int minScore) {

        Event event = eventRepository.findByNameAndStartDateTime(name, startDateTime).map(e -> {return e;}).orElse(null);

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
            event.setVisible(false);
            event.setCompleted(true);
            event.setMinScore(minScore);
            event.setOrganiser(organiserRepository.findById(1L).get());
        }

        event = eventRepository.saveAndFlush(event);
        return event;
    }
}
