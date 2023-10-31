package G3.jio.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import G3.jio.JioApplication;
import G3.jio.entities.AuthenticationResponse;
import G3.jio.entities.Student;
import G3.jio.entities.Role;
import G3.jio.repositories.StudentRepository;
import G3.jio.services.MailService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JioApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    private final String rootUrl = "http://localhost:";

    private final String apiUrl = "/api/v1/students";

    private final String loginUrl = "/api/v1/login/";

    private final String registerUrl = "/api/v1/auth/register";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private MailService mailService;

    private Student student;

    private String jwtToken;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();

        // admin account
        student = new Student();
        student.setName("admin");
        String encodedPassword = new BCryptPasswordEncoder().encode("securepassword");
        student.setEmail("admin@admin.com");
        student.setPassword(encodedPassword);
        student.setRole(Role.ADMIN);
        studentRepository.save(student);

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@admin.com", "securepassword");
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "test2");
            jsonObject.put("email", "hhuuuhh@test.com");
            jsonObject.put("password", "securepassword");
            jsonObject.put("userType", "S");
        } catch (JSONException e) {
        }
        HttpEntity<String> request = new HttpEntity<>(
                jsonObject.toString(),
                headers);

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
                "http://localhost:8080/api/v1/auth/register",
                HttpMethod.POST,
                request,
                AuthenticationResponse.class);

        // testing if organiser was created successfully
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Registration was successful, and the response contains a token.
        AuthenticationResponse responseBody = response.getBody();
        jwtToken = responseBody.getToken();

        // check that the jwtToken is not null
        System.out.println(jwtToken);
        assertNotNull(jwtToken);
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void testGetAllUsers_StudentsExist_StudentList() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = restTemplate.exchange(rootUrl + port + apiUrl, HttpMethod.GET,
                request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String responseString = result.getBody();
        try {
            Student[] responseStudentList = mapper.readValue(responseString, Student[].class);
            assertEquals(3, responseStudentList.length);
        } catch (Exception e) {
        }
    }

    @Test
    void testAddStudent_Successful_HttpCreated() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "newguy");
            jsonObject.put("email", "newGuyEmail@test.com");
            jsonObject.put("password", "password");
            jsonObject.put("userType", "S");
        } catch (JSONException e) {
        }
        HttpEntity<String> request = new HttpEntity<>(
                jsonObject.toString(),
                headers);

        ResponseEntity<AuthenticationResponse> result = restTemplate.exchange(
                rootUrl + port + registerUrl,
                HttpMethod.POST,
                request,
                AuthenticationResponse.class);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testGetStudentByEmail_Exist_Student() throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", "test@test.com");
        } catch (JSONException e) {

        }

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        URI uri = new URI(rootUrl + port + apiUrl + "email");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
        String responseString = result.getBody();
        try {
            Student responseStudent = mapper.readValue(responseString, Student.class);
            assertEquals(student.getEmail(), responseStudent.getEmail());
        } catch (Exception e) {

        }

    }

    @Test
    void testGetStudentByEmail_NotFound_Failure() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", "does@not.exist");
        } catch (JSONException e) {

        }

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        URI uri = new URI(rootUrl + port + apiUrl + "/email");

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
