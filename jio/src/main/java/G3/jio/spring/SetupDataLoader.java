package G3.jio.spring;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Organiser;
import G3.jio.entities.Role;
import G3.jio.entities.Status;
import G3.jio.entities.Student;
import G3.jio.repositories.EventRegistrationRepository;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final int NO_SETS = 4;
    
    private boolean alreadySetup = false;

    private final StudentRepository studentRepository;

    private final OrganiserRepository organiserRepository;

    private final EventRepository eventRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final EventRegistrationRepository eventRegistrationRepository;

    String hackString = "# Embark on a Journey to Tech Excellence with .Hack's DHCP Extravaganza\r\n" + //
            "\r\n" + //
            "Are you currently contemplating the idea of embarking on a thrilling and transformative journey to acquire a new and exciting tech-related skill? Perhaps you're in the midst of expanding your portfolio and looking for that elusive gem that can truly set you apart in the ever-evolving tech industry. Look no further, for behold, .Hack's much-anticipated Digital Hackathon and Certification Program (DHCP) is making a triumphant return in a grand and spectacular fashion this year, and it's not just bigger and better – it's an absolute tech extravaganza!\r\n" + //
            "\r\n" + //
            "## Why You Absolutely Can't Miss DHCP\r\n" + //
            "\r\n" + //
            "- Certification Glory Awaits: By participating in the DHCP, you will have the opportunity to pursue and attain the highly sought-after and prestigious AWS's Cloud Practitioner Certification. This isn't just any certification; it's a magnificent badge of honor that sets you on a path to tech excellence and opens doors to a world of possibilities, allowing you to scale the highest peaks of the tech industry.\r\n" + //
            "\r\n" + //
            "- Mentorship Like Never Before: Throughout this incredible journey, you won't be navigating the tech universe alone. You will be guided and mentored by our esteemed .Hack mentors, who aren't just experts in their fields; they are magical tech wizards who have successfully paved their way in the tech-savvy realm. Their mentorship will be nothing short of a life-transforming experience, a journey where you'll discover secrets of success that have been guarded like treasures in the tech industry.\r\n" + //
            "\r\n" + //
            "- Unleash Your Imagination: The DHCP isn't just about certification; it's about unlocking the doors to the limitless, enchanting world of technology. You will gain hands-on experience, network with fellow tech enthusiasts, and explore horizons you never dared to dream of. It's an experience that will kindle your imagination and inspire your inner tech artist, allowing you to paint a masterpiece in the canvas of technology.\r\n" + //
            "\r\n" + //
            "- Countless Learning Opportunities: Our DHCP event offers a myriad of learning experiences that cater to tech novices and experts alike. With a diverse array of workshops, presentations, and interactive sessions, you will be exposed to an abundance of knowledge that will stimulate your intellect and ignite your passion for technology. In every corner of the event, you'll discover opportunities to learn, grow, and expand your horizons in the vast realm of technology.\r\n" + //
            "\r\n" + //
            "- Inspiration at Every Turn: Prepare to be inspired, motivated, and awed by the incredible speakers and thought leaders from the tech industry who will grace our event. Their stories of triumph, innovation, and determination will fuel your ambition and drive to excel in the tech world, making you believe in the extraordinary potential that resides within you. You'll leave the event not just inspired but also ready to transform inspiration into action.\r\n" + //
            "\r\n" + //
            "- Community and Camaraderie: At DHCP, you won't just be a participant; you'll be part of a vibrant and thriving community of tech enthusiasts. The camaraderie that you'll experience at this event is unparalleled. You'll connect with like-minded individuals, form lasting friendships, and become part of a network that extends far beyond the event itself. You'll be part of something bigger, a community of tech adventurers ready to explore uncharted territories.\r\n" + //
            "\r\n" + //
            "### Event Details – Mark Your Calendar!\r\n" + //
            "Don't let this extraordinary opportunity slip through your fingers! Join us in this epic quest to unlock new dimensions of tech prowess and expand your professional horizons. .Hack's DHCP is not just an event; it's a magnificent journey into the heart of technology. We eagerly await your presence, and together, we'll script a legendary tale of technological achievement that will be etched in the annals of tech history. Join us, and let's soar to new heights in the tech-savvy cosmos! 🚀\r\n" + //
            "No adventure in the tech world has ever been this rewarding and exhilarating, and you are cordially invited to be a part of this extraordinary journey. As we prepare to make history, we welcome you to be a pioneer in the ever-evolving tech landscape. The opportunities are boundless, and the experiences are priceless. Your destiny in the tech world is calling – will you answer? The journey begins now, and it promises to be nothing short of legendary.";
    String wadString = "# Elevate Your Web Development Skills with .Hack's WAD2 Workshop Masterclass\r\n" + //
            "\r\n" + //
            "## The Significance of Web App Development\r\n" + //
            "\r\n" + //
            "In today's digital age, web application development plays a pivotal role in transforming ideas into functional, user-friendly digital experiences. The ability to create dynamic and engaging web applications is an essential skill for students and aspiring developers. With the growing demand for online services, the need for web developers who can design, build, and deploy web applications has never been greater.\r\n" + //
            "\r\n" + //
            "## The Challenge of Deployment\r\n" + //
            "\r\n" + //
            "One of the key challenges in web app development is the deployment phase. It's the moment when your carefully crafted application goes live and becomes accessible to users. This phase can be daunting, particularly for students who are still learning the ropes of web development. Proper deployment is crucial to ensure that your application runs smoothly, securely, and efficiently. \r\n" + //
            "\r\n" + //
            "## .Hack's Solution: WAD2 Workshop Masterclass\r\n" + //
            "\r\n" + //
            "Recognizing the importance of addressing this challenge, .Hack is excited to introduce the WAD2 Workshop Masterclass. This comprehensive workshop is designed to provide students with the knowledge and practical skills needed to deploy web applications effectively.\r\n" + //
            "\r\n" + //
            "### Unpacking AWS Services\r\n" + //
            "\r\n" + //
            "A central focus of the masterclass is understanding the wealth of services offered by Amazon Web Services (AWS). AWS is a powerhouse in the cloud computing industry, and gaining proficiency in its services can open doors to a world of opportunities in web development. \r\n" + //
            "\r\n" + //
            "- Amazon S3 (Simple Storage Service): As part of the workshop, we'll delve into Amazon S3, a versatile and reliable storage service. You'll learn how to leverage S3 for securely storing and distributing the static content of your web applications, such as images, stylesheets, and JavaScript files.\r\n" + //
            "\r\n" + //
            "- Amazon EC2 (Elastic Compute Cloud): We'll also demystify Amazon EC2, which provides scalable computing capacity. You'll understand how to set up virtual servers on EC2, ensuring your web application runs smoothly and can handle varying workloads.\r\n" + //
            "\r\n" + //
            "### The Power of CI/CD\r\n" + //
            "\r\n" + //
            "Continuous Integration and Continuous Deployment (CI/CD) are integral to modern web development practices. They streamline the deployment process, ensuring that changes to your web application are automatically tested, built, and deployed to production. In the workshop, we'll explore CI/CD tools and best practices:\r\n" + //
            "\r\n" + //
            "- Automation: CI/CD tools automate the integration, testing, and deployment process, reducing the risk of human errors and ensuring that your application is always in a deployable state.\r\n" + //
            "\r\n" + //
            "- Faster Release Cycles: Implementing CI/CD enables you to release new features, bug fixes, and updates more frequently, keeping your application up to date and competitive.\r\n" + //
            "\r\n" + //
            "- Consistency and Reliability: CI/CD pipelines create a consistent and reliable deployment process. When an update is ready, you can confidently release it knowing that the process has been thoroughly tested.\r\n" + //
            "\r\n" + //
            "- Scalability: CI/CD makes it easier to scale your application. As demand grows, you can easily adjust your infrastructure and deploy updates to meet the needs of your users.\r\n" + //
            "\r\n" + //
            "### Realistic Project Deployment\r\n" + //
            "\r\n" + //
            "To provide you with a hands-on understanding of web app deployment, we will demonstrate a realistic project deployment during the workshop. You'll have the opportunity to witness the entire deployment process from start to finish. This practical demonstration will leave you with a tangible understanding of deploying web applications using AWS services and CI/CD tools.\r\n" + //
            "\r\n" + //
            "## Event Details\r\n" + //
            "\r\n" + //
            "Don't miss this golden opportunity to expand your knowledge and build essential skills for your web development journey. .Hack's WAD2 Workshop Masterclass promises to be an invaluable experience for all participants.\r\n" + //
            "By joining us, you'll not only gain knowledge and skills but also join a community of like-minded individuals passionate about web development. You'll form lasting connections, receive ongoing support, and become part of a network that extends far beyond the workshop.\r\n" + //
            "\r\n" + //
            "## Conclusion\r\n" + //
            "\r\n" + //
            "Mark your calendars and get ready to level up your web app development skills with .Hack! As you step into the world of web development, remember that mastering the deployment phase is an essential step toward becoming a proficient developer. With the right knowledge and skills, you can confidently launch your web applications into the digital sphere, ready to make an impact.\r\n" + //
            "\r\n" + //
            "Your journey in web app development is about to become even more exciting and rewarding. Join us for the WAD2 Workshop Masterclass and be part of a transformative experience that will shape your future as a web developer. The opportunities are boundless, and the experiences are priceless. Your destiny in the tech world is calling – will you answer? The journey begins now, and it promises to be nothing short of legendary.\r\n" + //
            "\r\n" + //
            "We look forward to having you on board as we embark on this educational adventure. Let's soar to new heights in the tech-savvy cosmos! 🚀";
            
    // API

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial users
        createStudentIfNotFound("admin", "admin@admin.com", "admin", Role.ADMIN);
        createOrganiserIfNotFound("organiser", "org@org.com", "organiser", Role.ORGANISER);

        for (int i = 1; i <= NO_SETS * 5; i++) {
            String name = "student" + Integer.toString(i);
            String email = name + "@student.com";
            createStudentIfNotFound(name, email, "student", Role.STUDENT);
        }

        // == create events
        createCompletedEventIfNotFound(".Hack Social Night-dummy", LocalDateTime.of(2023, 10, 1, 7, 0),
                LocalDateTime.of(2023, 10, 1, 19, 0), "SCIS1 B1 Alcove",
                "/socialnight.jpg", NO_SETS * 5, "FCFS",
                "Hi All! It is with great pleasure that we invite you to .Hack’s inaugural Social Night 2023! Social Night will serve as a networking platform for you to gain insightful knowledge through the distinguished speakers from companies such as Credit Suisse. You will also get the chance to interact with the speakers and other .Hack members during the event itself. .Hack hopes to provide you with the best experiences, to better prepare you for the technology industry in the workforce. As such, we sincerely hope that you will consider attending Social Night 2023 with us!",
             0);

        createCompletedEventIfNotFound("Ellipsis Back2Sku Welfare Drive-dummy", LocalDateTime.of(2023, 10, 2, 7, 0),
                LocalDateTime.of(2023, 10, 2, 19, 0), "SCIS1 Basement (near Big Steps)",
                "/back2skuwelfaredrive.jpg", NO_SETS * 5, "FCFS",
                "Got the back-to-school blues? Well, you don’t have to dwell on it – because Ellipsis is ready to banish them with our upcoming Back2Sku Welfare Drive! We have packaged snacks and drinks for you, as well as chocolate ice cream and customised Taiwanese dessert bowls, so make sure you don’t miss out!",
             0);

        createCompletedEventIfNotFound(".Hack WAD2 Workshop 2023-dummy", LocalDateTime.of(2023, 10, 3, 7, 0),
                LocalDateTime.of(2023, 10, 3, 19, 0), "SCIS1 Seminar Room B1-1", "/wad2workshop.jpg", NO_SETS * 5, "FCFS",
                "Are you taking WAD2 (Web App Development 2) this year? Need a crash course on how to deploy your project? Don't sweat it! .Hack have put together a WAD2 Workshop masterclass! It aims to help students get a better understanding of AWS services and how to make use of them for your project. We will be going through AWS using S3 and EC2, CI/CD tools and demonstrating a realistic project deployed onto a live website.",
             0);

        createCompletedEventIfNotFound(".Hack Certification Programme (DHCP)-dummy", LocalDateTime.of(2023, 10, 4, 7, 0),
                LocalDateTime.of(2023, 10, 4, 19, 0), "SCIS2 Computing Lab B1-1", "/dhcpworkshop.jpg", NO_SETS * 5, "FCFS",
                "Thinking of picking up a new tech-related skill? Or looking for something that will boost your portfolio? Look no further, .Hack’s much-anticipated DHCP is back this year! By participating in the DHCP, you will be able to take the coveted AWS’s Cloud Practitioner Certification, under the guidance of our experienced .Hack mentors!",
             0);
                
        // Actual
        createEventIfNotFound(".Hack Social Night", LocalDateTime.of(2023, 10, 1, 7, 0),
                LocalDateTime.of(2023, 10, 1, 19, 0), "SCIS1 B1 Alcove",
                "/socialnight.jpg", 1, "FCFS",
                hackString,
                true, 0);

        createEventIfNotFound("Ellipsis Back2Sku Welfare Drive", LocalDateTime.of(2023, 10, 2, 7, 0),
                LocalDateTime.of(2023, 10, 2, 19, 0), "SCIS1 Basement (near Big Steps)",
                "/back2skuwelfaredrive.jpg", 5, "FCFS",
                "Got the back-to-school blues? Well, you don’t have to dwell on it – because Ellipsis is ready to banish them with our upcoming Back2Sku Welfare Drive! We have packaged snacks and drinks for you, as well as chocolate ice cream and customised Taiwanese dessert bowls, so make sure you don’t miss out!",
                true, 0);

        createEventIfNotFound(".Hack WAD2 Workshop 2023", LocalDateTime.of(2023, 10, 3, 7, 0),
                LocalDateTime.of(2023, 10, 3, 19, 0), "SCIS1 Seminar Room B1-1", "/wad2workshop.jpg", 10, "FCFS",
                wadString,
                true, 0);

        createEventIfNotFound(".Hack Certification Programme (DHCP)", LocalDateTime.of(2023, 10, 4, 7, 0),
                LocalDateTime.of(2023, 10, 4, 19, 0), "SCIS2 Computing Lab B1-1", "/dhcpworkshop.jpg", 15, "FCFS",
                "Thinking of picking up a new tech-related skill? Or looking for something that will boost your portfolio? Look no further, .Hack’s much-anticipated DHCP is back this year! By participating in the DHCP, you will be able to take the coveted AWS’s Cloud Practitioner Certification, under the guidance of our experienced .Hack mentors!",
                true, 0);


        // create registrations
        for (int i = 0; i < NO_SETS; i ++) {
            createCompletedEventRegistrationIfNotFound(2L + 5*i, 1L, false);
            createCompletedEventRegistrationIfNotFound(2L + 5*i, 2L, false);
            createCompletedEventRegistrationIfNotFound(2L + 5*i, 3L, false);
            createCompletedEventRegistrationIfNotFound(2L + 5*i, 4L, false);

            createCompletedEventRegistrationIfNotFound(3L + 5*i, 1L, true);
            createCompletedEventRegistrationIfNotFound(3L + 5*i, 2L, false);
            createCompletedEventRegistrationIfNotFound(3L + 5*i, 3L, false);
            createCompletedEventRegistrationIfNotFound(3L + 5*i, 4L, false);

            createCompletedEventRegistrationIfNotFound(4L + 5*i, 1L, true);
            createCompletedEventRegistrationIfNotFound(4L + 5*i, 2L, true);
            createCompletedEventRegistrationIfNotFound(4L + 5*i, 3L, false);
            createCompletedEventRegistrationIfNotFound(4L + 5*i, 4L, false);

            createCompletedEventRegistrationIfNotFound(5L + 5*i, 1L, true);
            createCompletedEventRegistrationIfNotFound(5L + 5*i, 2L, true);
            createCompletedEventRegistrationIfNotFound(5L + 5*i, 3L, true);
            createCompletedEventRegistrationIfNotFound(5L + 5*i, 4L, false);

            createCompletedEventRegistrationIfNotFound(6L + 5*i, 1L, true);
            createCompletedEventRegistrationIfNotFound(6L + 5*i, 2L, true);
            createCompletedEventRegistrationIfNotFound(6L + 5*i, 3L, true);
            createCompletedEventRegistrationIfNotFound(6L + 5*i, 4L, true);
        }

        // sign up to new event
        for (int i = 0; i < NO_SETS; i++) {
            createEventRegistrationIfNotFound(2L + 5*i, 5L, true);
            createEventRegistrationIfNotFound(3L + 5*i, 5L, true);
            createEventRegistrationIfNotFound(4L + 5*i, 5L, true);
            createEventRegistrationIfNotFound(5L + 5*i, 5L, true);
            createEventRegistrationIfNotFound(6L + 5*i, 5L, true);
        }

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
            student.setMatricNo("87654321");
            student.setPhone("12345678");
        }

        student = studentRepository.saveAndFlush(student);
        return student;
    }

    @Transactional
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

    @Transactional
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
        er.setStatus(Status.ACCEPTED);

        studentRepository.findById(studentId).get().addEventRegistration(er);

        eventRepository.findById(eventId).get().addEventRegistration(er);

        eventRegistrationRepository.saveAndFlush(er);
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

        organiser = organiserRepository.saveAndFlush(organiser);
        return organiser;
    }

    @Transactional
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

    @Transactional
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
