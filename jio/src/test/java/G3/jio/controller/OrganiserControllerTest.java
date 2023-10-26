// package G3.jio.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.mail.javamail.JavaMailSender;

// import G3.jio.JioApplication;
// import G3.jio.controllers.OrganiserController;
// import G3.jio.entities.Organiser;
// import G3.jio.services.MailService;

// @SpringBootTest(classes = JioApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
// public class OrganiserControllerTest {
//     private final String rootUrl = "http://localhost:";

//     private final String apiUrl = "/api/v1/organiser/";

//     private final String loginUrl = "/api/v1/login/";

//     @Autowired
//     private TestRestTemplate restTemplate;

//     @Autowired
//     private OrganiserController organiserController;

//     @MockBean
//     private JavaMailSender javaMailSender;
    
//     @MockBean
//     private MailService mailService;

//     private Organiser organiser;

//     private String jwtToken;
// }
