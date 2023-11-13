package G3.jio.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import G3.jio.JioApplication;
import G3.jio.entities.AuthenticationResponse;
import G3.jio.entities.Event;
import G3.jio.entities.Organiser;
import G3.jio.entities.Role;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.services.MailService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JioApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrganiserControllerTest {

    private final String rootUrl = "http://localhost:";

    private final String apiUrl = "/api/v1/organisers";

    private final String loginUrl = "/api/v1/login/";

    private final String registerUrl = "/api/v1/auth/register";

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
        organiserRepository.deleteAll();
        organiser = new Organiser();
        organiser.setId(1L); // ID for testing only
        organiser.setName("test");
        String encodedPassword = new BCryptPasswordEncoder().encode("password");
        organiser.setEmail("test@test.com");
        organiser.setPassword(encodedPassword);
        organiser.setRole(Role.ORGANISER);
        organiserRepository.save(organiser);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "test2");
            jsonObject.put("email", "test2@test.com");
            jsonObject.put("password", "password");
            jsonObject.put("userType", "O");
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
        assertNotNull(jwtToken);
    }

    @AfterEach
    void tearDown() {
        organiserRepository.deleteAll();
    }

    @Test
    void testGetAllUsers_OrganisersExist_OrganiserList() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> result = restTemplate.exchange(rootUrl + port + apiUrl, HttpMethod.GET,
                request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String responseString = result.getBody();
        try {
            Organiser[] responseOrganiserList = mapper.readValue(responseString, Organiser[].class);
            assertEquals(2, responseOrganiserList.length);
        } catch (Exception e) {
        }
    }

    @Test
    void testAddOrganiser_Successful_HttpCreated() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "new guy");
            jsonObject.put("email", "newGuyEmail@test.com");
            jsonObject.put("password", "password");
            jsonObject.put("userType", "O");
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
    void testGetOrganiserByEmail_Exist_Organiser() throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", "test2@test.com");
        } catch (JSONException e) {

        }

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        URI uri = new URI(rootUrl + port + apiUrl + "email");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
        String responseString = result.getBody();
        try {
            Organiser responseOrganiser = mapper.readValue(responseString, Organiser.class);
            assertEquals(organiser.getEmail(), responseOrganiser.getEmail());
        } catch (Exception e) {

        }

    }

    @Test
    void testGetOrganiserByEmail_NotFound_Failure() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
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

    // WIP: delete endpoint is not completed yet
    // @Test
    // void testDeleteOrganiserById_Success() throws URISyntaxException {
    // HttpHeaders headers = new HttpHeaders();
    // headers.setBasicAuth("admin@admin.com", "admin");
    // headers.setContentType(MediaType.APPLICATION_JSON);

    // HttpEntity<String> request = new HttpEntity<>(headers);
    // URI uri = new URI(rootUrl + port + apiUrl + "/id/2"); // organiser saved is 1

    // ResponseEntity<String> result = restTemplate.exchange(uri,
    // HttpMethod.DELETE, request, String.class);
    // assertEquals(HttpStatus.OK, result.getStatusCode());
    // }

    // @Test
    // void testDeleteOrganiserById_NotFound_Failure() throws URISyntaxException {

    // HttpHeaders headers = new HttpHeaders();
    // headers.setBasicAuth("admin@admin.com", "admin");
    // headers.setContentType(MediaType.APPLICATION_JSON);

    // HttpEntity<String> request = new HttpEntity<>(headers);
    // URI uri = new URI(rootUrl + port + apiUrl + "/id/2"); // organiser saved is 2

    // ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.DELETE,
    // request, String.class);
    // assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    // }

    @Test
    void testPostEvent_Success_ReturnEvent() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("event",
                "{\"startDateTime\": \"2001-11-11T11:11:11.111Z\", \"name\": \"imagetest1\", \"endDateTime\": \"2001-11-11T11:11:11.111Z\", \"description\":\"please\", \"capacity\":\"1\"}");
        parts.add("image", null);

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create an HttpEntity with the multipart data and headers
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts, headers);

        ResponseEntity<String> result = restTemplate.exchange(rootUrl + port + apiUrl + "/create-event",
                HttpMethod.POST,
                requestEntity, String.class);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testPostEvent_Fail_BadRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "test event");
            jsonObject.put("description", "description description description");
            jsonObject.put("capacity", 1);
            // jsonObject.put("endDateTime", "2001-11-11T11:11:11.111Z"); // missing start
            // date
            jsonObject.put("startDateTime", "2001-11-11T11:11:11.111Z");
        } catch (JSONException e) {
        }

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.exchange(rootUrl + port + apiUrl + "/create-event",
                HttpMethod.POST,
                request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void testGetEventsByOrganiserEmail_Success() throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("event",
                "{\"startDateTime\": \"2001-11-11T11:11:11.111Z\", \"name\": \"test1\", \"endDateTime\": \"2001-11-11T11:11:11.111Z\", \"description\":\"please\", \"capacity\":\"1\"}");
        parts.add("image", null);

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create an HttpEntity with the multipart data and headers
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts, headers);

        ResponseEntity<String> result = restTemplate.exchange(rootUrl + port + apiUrl + "/create-event",
                HttpMethod.POST,
                requestEntity, String.class);

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        parts = new LinkedMultiValueMap<>();
        parts.add("event",
                "{\"startDateTime\": \"2001-11-11T11:11:11.111Z\", \"name\": \"test2\", \"endDateTime\": \"2001-11-11T11:11:11.111Z\", \"description\":\"please\", \"capacity\":\"1\"}");
        parts.add("image", null);

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create an HttpEntity with the multipart data and headers
        requestEntity = new HttpEntity<>(parts, headers);

        result = restTemplate.exchange(rootUrl + port + apiUrl + "/create-event",
                HttpMethod.POST,
                requestEntity, String.class);

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", "test2@test.com");
        } catch (JSONException e) {
        }

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        URI uri = new URI(rootUrl + port + apiUrl + "/email/events");

        result = restTemplate.exchange(uri, HttpMethod.POST,
                request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String responseString = result.getBody();
        try {
            Event[] responseEventList = mapper.readValue(responseString, Event[].class);
            assertEquals(2, responseEventList.length);
        } catch (Exception e) {
        }
    }

}
