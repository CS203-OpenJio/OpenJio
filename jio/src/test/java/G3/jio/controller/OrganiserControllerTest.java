package G3.jio.controller;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import G3.jio.JioApplication;
import G3.jio.entities.AuthenticationResponse;
import G3.jio.entities.Organiser;
import G3.jio.entities.Role;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.services.MailService;

@SpringBootTest(classes = JioApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrganiserControllerTest {
    private final String rootUrl = "http://localhost:";

    private final String apiUrl = "/api/v1/organiser/";

    private final String loginUrl = "/api/v1/login/";

    private final String registerUrl = "/api/v1/register/";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrganiserRepository organiserRepository;

    @MockBean
    private JavaMailSender javaMailSender;
    
    @MockBean
    private MailService mailService;

    private Organiser organiser;

    private String jwtToken;

    @BeforeEach
    void setUp() {

        // organiser = new Organiser();
        // organiser.setName("test");
        // String encodedPassword = new BCryptPasswordEncoder().encode("password");
        // organiser.setEmail("test@test.com");
        // organiser.setPassword(encodedPassword);
        // organiser.setRole(Role.ORGANISER);
        // organiserRepository.save(organiser);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "test");
            jsonObject.put("email", "test@test.com");
            jsonObject.put("password", "password");
            jsonObject.put("userType", "O");
        } catch (JSONException e) {
            
        }
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

        
        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange("http://localhost:8080/api/v1/auth/register", HttpMethod.POST, request, AuthenticationResponse.class);
        
        // for testing if organiser was created successfully
        // assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        
        // Registration was successful, and the response contains a token.
        AuthenticationResponse responseBody = response.getBody();
        jwtToken = responseBody.getToken();

    }


    @Test
    void testGetAllUsers_OrganisersExist_OrganiserList() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("admin@admin.com", "admin");
        HttpEntity<String> request = new HttpEntity<>(headers);

        URI uri = new URI(rootUrl + port + apiUrl);
        Organiser organiser = new Organiser();
        organiser.setName("test2");
        organiser.setEmail("test2@test.com");
        organiser.setPassword(new BCryptPasswordEncoder().encode("password"));
        organiser.setRole(Role.ORGANISER);
        organiserRepository.save(organiser);
        
        ResponseEntity<Organiser[]> result = restTemplate.exchange(rootUrl + port + apiUrl, HttpMethod.GET, request, Organiser[].class);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        Organiser[] organiserList = result.getBody();
        assertEquals(3, organiserList.length);
    }
}
