package G3.jio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import G3.jio.DTO.LoginDTO;
import G3.jio.DTO.RegistrationDTO;
import G3.jio.config.jwt.JwtService;
import G3.jio.entities.AuthenticationResponse;
import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.exceptions.FailedRegistrationException;
import G3.jio.exceptions.InvalidUserTypeException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;

class AuthServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private OrganiserRepository organiserRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private Student student;

    private Organiser organiser;

    private RegistrationDTO registrationDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        student = new Student();
        student.setName("student");
        student.setEmail("student@test.com");
        student.setPassword("password");

        organiser = new Organiser();
        organiser.setName("organiser");
        organiser.setEmail("organiser@test.com");
        organiser.setPassword("password");

        registrationDTO = new RegistrationDTO();
        registrationDTO.setName("test");
        registrationDTO.setEmail("test@test.com");
        registrationDTO.setPassword("password");

    }

    @Test
    void authenticateUser_StudentLogin_Success() {
        // Arrange
        when(studentRepository.existsByEmail(anyString())).thenReturn(true);
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(new Student()));
        when(jwtService.generateToken(any())).thenReturn("mockedToken");

        // Act
        LoginDTO loginDTO = new LoginDTO("student@test.com", "password");
        AuthenticationResponse response = authService.authenticateUser(loginDTO);

        // Assertions
        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(studentRepository).existsByEmail("student@test.com");
        verify(studentRepository).findByEmail("student@test.com");
        verify(jwtService).generateToken(any());
    }

    @Test
    void authenticateUser_OrganiserLogin_Success() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("organiser@test.com", "password");
        when(organiserRepository.existsByEmail(anyString())).thenReturn(true);
        when(organiserRepository.findByEmail(anyString())).thenReturn(Optional.of(organiser));
        when(jwtService.generateToken(any())).thenReturn("mockedToken");

        // Act
        AuthenticationResponse response = authService.authenticateUser(loginDTO);

        // Assertions
        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(organiserRepository).existsByEmail("organiser@test.com");
        verify(organiserRepository).findByEmail("organiser@test.com");
        verify(jwtService).generateToken(any());
    }

    @Test
    void authenticateUser_InvalidEmailLogin_UserNotFoundExceptionThrown() {
        // Arrange
        String exceptionMessage = "";
        AuthenticationResponse response = new AuthenticationResponse();
        LoginDTO loginDTO = new LoginDTO("test@test.com", "password");
        when(organiserRepository.existsByEmail(anyString())).thenReturn(false);
        when(studentRepository.existsByEmail(anyString())).thenReturn(false);

        // Act
        try {
            response = authService.authenticateUser(loginDTO);
        } catch (UserNotFoundException e) {
            exceptionMessage = e.getMessage();
        }

        // Assertions
        assertEquals("User Not Found: No Such User", exceptionMessage);

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(organiserRepository).existsByEmail("test@test.com");
        verify(studentRepository).existsByEmail("test@test.com");

    }

    @Test
    void registerUser_StudentRegistration_Success() {
        // Arrange
        registrationDTO.setUserType('S');

        when(studentRepository.existsByEmail(anyString())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(jwtService.generateToken(any())).thenReturn("mockedToken");

        // Act
        AuthenticationResponse response = authService.registerUser(registrationDTO);

        // Assertions
        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());

        // Verify
        verify(studentRepository).existsByEmail(anyString());
        verify(bCryptPasswordEncoder).encode(anyString());
        verify(studentRepository).save(any());
        verify(jwtService).generateToken(any());
    }

    @Test
    void registerUser_OrganiserRegistration_Success() {
        // Arrange
        registrationDTO.setUserType('O');

        when(organiserRepository.existsByEmail(anyString())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(jwtService.generateToken(any())).thenReturn("mockedToken");

        // Act
        AuthenticationResponse response = authService.registerUser(registrationDTO);

        // Assertions
        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());

        // Verify
        verify(organiserRepository).existsByEmail(anyString());
        verify(bCryptPasswordEncoder).encode(anyString());
        verify(organiserRepository).save(any());
        verify(jwtService).generateToken(any());
    }

    @Test
    void registerUser_InvalidUserType_InvalidUserTypeExceptionThrown() {

        // Arrange
        String exceptionMessage = "";
        registrationDTO.setUserType('X');

        // Act
        try {
            authService.registerUser(registrationDTO);
        } catch (InvalidUserTypeException e) {
            exceptionMessage = e.getMessage();
        }

        // Assertions
        assertEquals("Invalid User Type: User type is invalid!", exceptionMessage);
    }

    @Test
    void registerStudent_DuplicateEmail_FailedRegistrationExceptionThrown() {

        // Arrange
        String exceptionMessage = "";
        registrationDTO.setUserType('S');
        when(studentRepository.existsByEmail(anyString())).thenReturn(true);

        // Act
        try {
            authService.registerUser(registrationDTO);
        } catch (FailedRegistrationException e) {
            exceptionMessage = e.getMessage();
        }

        // Assert
        assertEquals("Registration Failed: Email already taken!", exceptionMessage);

        // Verify
        verify(studentRepository).existsByEmail(anyString());
    }

    @Test
    void registerOrganiser_DuplicateEmail_FailedRegistrationExceptionThrown() {

        // Arrange
        String exceptionMessage = "";
        registrationDTO.setUserType('O');
        when(organiserRepository.existsByEmail(anyString())).thenReturn(true);

        // Act
        try {
            authService.registerUser(registrationDTO);
        } catch (FailedRegistrationException e) {
            exceptionMessage = e.getMessage();
        }

        // Assert
        assertEquals("Registration Failed: Email already taken!", exceptionMessage);

        // Verify
        verify(studentRepository).existsByEmail(anyString());
    }
}
