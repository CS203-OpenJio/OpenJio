package G3.jio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import G3.jio.entities.Student;
import G3.jio.exceptions.AlreadyExistsException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;

public class ResetPasswordServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private OrganiserRepository organiserRepository;

    @Mock
    private ChangeCredentialService changeCredentialService;

    @Mock
    private MailService mailService;

    @InjectMocks
    private ResetPasswordService resetPasswordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void setStudentResetPasswordTokenAndSendEmail_Success() {
        // Arrange
        String email = "student@test.com";
        Student student = new Student();
        student.setEmail(email);

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));

        // Act
        ResponseEntity<String> response = resetPasswordService.setResetPasswordTokenAndSendEmail(email);

        // Assert
        verify(mailService).sendSimpleMessage(eq(email), eq("OpenJio Password Reset"), anyString());
        assertNotNull(student.getResetPasswordToken());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("An email has been sent to you. Please check it for a code to reset your password!", response.getBody());
    }


    @Test
    void setResetPasswordTokenAndSendEmail_Failure_ThrowUserNotFound() {
        // Arrange
        String email = "test@test.com";
        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(organiserRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> resetPasswordService.setResetPasswordTokenAndSendEmail(email));
    }

    @Test
    void setResetPasswordTokenAndSendEmail_Failure_TokenAlreadyExists() {
        // Arrange
        String email = "student@test.com";
        Student student = new Student();
        student.setEmail(email);
        student.setResetPasswordToken("existingToken");

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));

        // Act and Assert
        assertThrows(AlreadyExistsException.class, () -> resetPasswordService.setResetPasswordTokenAndSendEmail(email));
    }

}
