package G3.jio.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;
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
import org.springframework.test.context.junit4.SpringRunner;

import G3.jio.JioApplication;
import G3.jio.entities.AuthenticationResponse;
import G3.jio.entities.Organiser;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.services.MailService;

@RunWith(SpringRunner.class)
@SpringBootTest(
  classes = JioApplication.class,
  webEnvironment = WebEnvironment.RANDOM_PORT
)
public class OrganiserControllerTest {

  private final String rootUrl = "http://localhost:";

  private final String apiUrl = "/api/v1/organisers/";

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
    } catch (JSONException e) {}
    HttpEntity<String> request = new HttpEntity<>(
      jsonObject.toString(),
      headers
    );

    ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
      "http://localhost:8080/api/v1/auth/register",
      HttpMethod.POST,
      request,
      AuthenticationResponse.class
    );

    // for testing if organiser was created successfully
    // assertEquals(response.getStatusCode(), HttpStatus.CREATED);

    // Registration was successful, and the response contains a token.
    AuthenticationResponse responseBody = response.getBody();
    jwtToken = responseBody.getToken();

    // check that the jwtToken is not null
    assertNotNull(jwtToken);
  }

  @Test
  void testGetAllUsers_OrganisersExist_OrganiserList() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + jwtToken);
    headers.setContentType(MediaType.APPLICATION_JSON);
    // headers.setBasicAuth("admin@admin.com", "admin");
    // headers.setBearerAuth(jwtToken);
    HttpEntity<String> request = new HttpEntity<>(headers);

    // ResponseEntity<Organiser[]> result = restTemplate.withBasicAuth("admin@admin.com", "admin").getForEntity(rootUrl + "8080" + apiUrl, Organiser[].class);
    ResponseEntity<Organiser[]> result = restTemplate.exchange(
      "http://localhost:8080/api/v1/organisers",
      HttpMethod.GET,
      request,
      Organiser[].class
    );
    assertEquals(result.getStatusCode(), HttpStatus.OK);
    // Organiser[] organiserList = result.getBody();
    // assertEquals(3, organiserList.length);
  }

  @Test
  void testAddOrganiser_Successful() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("name", "test2");
      jsonObject.put("email", "test2@test2.com");
      jsonObject.put("password", "password");
      jsonObject.put("userType", "O");
    } catch (JSONException e) {}
    HttpEntity<String> request = new HttpEntity<>(
      jsonObject.toString(),
      headers
    );

    ResponseEntity<AuthenticationResponse> result = restTemplate.exchange(
      rootUrl + port + registerUrl,
      HttpMethod.POST,
      request,
      AuthenticationResponse.class
    );
    assertEquals(HttpStatus.CREATED, result.getStatusCode());
  }

    @Test
    void testGetOrganiserByEmail_Exist_Organiser() throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", "test@test.com");
        } catch (JSONException e) {

        }

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        URI uri = new URI(rootUrl + port + apiUrl);

        ResponseEntity<Organiser> result = restTemplate.exchange(uri, HttpMethod.POST, request, Organiser.class);
        Organiser responseOrganiser = result.getBody();


        assertEquals(organiser, responseOrganiser);
    }

    // WIP: Similar error to first test
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
        URI uri = new URI(rootUrl + port + apiUrl + "email");

        ResponseEntity<Organiser> result = restTemplate.exchange(uri, HttpMethod.POST, request, Organiser.class);
        Organiser responseOrganiser = result.getBody();

        assertEquals(404, result.getStatusCode().value());
    }
}
