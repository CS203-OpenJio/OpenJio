package G3.jio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.exceptions.InvalidUserTypeException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;

@TestComponent
class ChangeCredentialServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private OrganiserRepository organiserRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private ChangeCredentialService changeCredentialService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void replacePassword_StudentExists_ReturnResponseEntity() {

        // Arrange
        String email = "test@test.com";
        String newPassword = "password";
        Character userType = 'S';
        Student student = new Student();
        student.setEmail(email);
        ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>("Successfully changed password", HttpStatus.OK);

        when(studentRepository.findByEmail(any(String.class))).thenReturn(Optional.of(student));
        when(studentRepository.saveAndFlush(any(Student.class))).thenReturn(student);

        // Act
        ResponseEntity<String> responseEntity = changeCredentialService.replacePassword(email, newPassword, userType);

        // Assert
        assertEquals(expectedResponseEntity, responseEntity);
        verify(studentRepository, times(1)).findByEmail(email);
    }

    @Test
    void replacePassword_StudentNotFound_ReturnUserNotFound() {

        String exceptionMsg = "";
        // Arrange
        String email = "test@test.com";
        when(studentRepository.findByEmail(any(String.class))).thenThrow(new UserNotFoundException("Student does not exist!"));

        // Act
        try {
            Object o = changeCredentialService.replacePassword(email, "password", 'S');
        } catch (UserNotFoundException e) {
            exceptionMsg = e.getMessage();
        }
        // Assert
        assertEquals("User Not Found: Student does not exist!", exceptionMsg);
        verify(studentRepository, times(1)).findByEmail(email);
    }

    @Test
    void replacePassword_OrganiserExists_ReturnResponseEntity() {

        // Arrange
        String email = "test@test.com";
        String newPassword = "newpassword";
        Character userType = 'O';
        Organiser organiser = new Organiser();
        organiser.setEmail(email);
        ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>("Successfully changed password", HttpStatus.OK);

        when(organiserRepository.findByEmail(any(String.class))).thenReturn(Optional.of(organiser));
        when(organiserRepository.saveAndFlush(any(Organiser.class))).thenReturn(organiser);

        // Act
        ResponseEntity<String> responseEntity = changeCredentialService.replacePassword(email, newPassword, userType);

        // Assert
        assertEquals(expectedResponseEntity, responseEntity);
        verify(organiserRepository, times(1)).findByEmail(email);
    }

    @Test
    void replacePassword_OrganiserNotFound_ReturnUserNotFound() {

        String exceptionMsg = "";
        // Arrange
        String email = "test@test.com";
        when(organiserRepository.findByEmail(any(String.class))).thenThrow(new UserNotFoundException("Organiser does not exist!"));

        // Act
        try {
            Object o = changeCredentialService.replacePassword(email, "password", 'O');
        } catch (UserNotFoundException e) {
            exceptionMsg = e.getMessage();
        }
        // Assert
        assertEquals("User Not Found: Organiser does not exist!", exceptionMsg);
        verify(organiserRepository, times(1)).findByEmail(email);
    }

    @Test
    void replacePasswordInvalidUserType_Failure() {
        String exceptionMsg = "";
        // Arrange
        String email = "user@example.com";
        String newPassword = "newPassword";
        char userType = 'X'; // Invalid user type

        // Act
        try {
            changeCredentialService.replacePassword(email, newPassword, userType);
        } catch (InvalidUserTypeException e) {
            exceptionMsg = e.getMessage();
        }

        // Assert
        assertEquals("Invalid User Type: User type is invalid!", exceptionMsg);

        // Verify that repository methods are not called
        verifyNoInteractions(studentRepository);
        verifyNoInteractions(organiserRepository);
    }
}
