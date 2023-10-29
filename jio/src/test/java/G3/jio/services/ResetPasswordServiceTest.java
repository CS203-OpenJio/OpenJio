package G3.jio.services;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.exceptions.AlreadyExistsException;
import G3.jio.exceptions.NotExistException;
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

    private Student student;

    private Organiser organiser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        student = new Student();
        student.setEmail("student@test.com");

        organiser = new Organiser();
        organiser.setEmail("organiser@test.com");
    }

    @Test
    void setResetPasswordTokenAndSendEmail_Student_Success() {
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
        assertEquals("An email has been sent to you. Please check it for a code to reset your password!",
                response.getBody());
    }

    @Test
    void setResetPasswordTokenAndSendEmail_Organiser_Success() {
        // Arrange
        String email = "organiser@test.com";
        Organiser organiser = new Organiser();
        organiser.setEmail(email);

        when(organiserRepository.findByEmail(email)).thenReturn(Optional.of(organiser));

        // Act
        ResponseEntity<String> response = resetPasswordService.setResetPasswordTokenAndSendEmail(email);

        // Assert
        verify(mailService).sendSimpleMessage(eq(email), eq("OpenJio Password Reset"), anyString());
        assertNotNull(organiser.getResetPasswordToken());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("An email has been sent to you. Please check it for a code to reset your password!",
                response.getBody());
    }

    @Test
    void setResetPasswordTokenAndSendEmail_StudentEmailNotMatch_ThrowIllegalArgumentException() {

        String exceptionMsg = "";
        // Arrange
        String email = "does@not.exist";
        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));
        when(organiserRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        try {
            resetPasswordService.setResetPasswordTokenAndSendEmail(email);
        } catch (IllegalArgumentException e) {
            exceptionMsg = e.getMessage();
        }

        // Assert
        assertEquals("Email entered is incorrect", exceptionMsg);
    }

    @Test
    void setResetPasswordTokenAndSendEmail_OrganiserEmailNotMatch_ThrowIllegalArgumentException() {

        String exceptionMsg = "";
        // Arrange
        String email = "does@not.exist";
        when(organiserRepository.findByEmail(email)).thenReturn(Optional.of(organiser));
        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        try {
            resetPasswordService.setResetPasswordTokenAndSendEmail(email);
        } catch (IllegalArgumentException e) {
            exceptionMsg = e.getMessage();
        }

        // Assert
        assertEquals("Email entered is incorrect", exceptionMsg);
    }

    @Test
    void setResetPasswordTokenAndSendEmail_StudentTokenExist_TokenAlreadyExists() {
        String exceptionMsg = "";

        // Arrange
        String email = "student@test.com";
        student.setResetPasswordToken("existingToken");

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));
        when(organiserRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        try {
            resetPasswordService.setResetPasswordTokenAndSendEmail(email);
        } catch (AlreadyExistsException e) {
            exceptionMsg = e.getMessage();
        }

        // Assert
        assertEquals("Reset password token already exists!", exceptionMsg);
    }

    @Test
    void setResetPasswordTokenAndSendEmail_OrganiserTokenExist_TokenAlreadyExists() {
        String exceptionMsg = "";

        // Arrange
        String email = "organiser@test.com";
        organiser.setResetPasswordToken("existingToken");

        when(organiserRepository.findByEmail(email)).thenReturn(Optional.of(organiser));
        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        try {
            resetPasswordService.setResetPasswordTokenAndSendEmail(email);
        } catch (AlreadyExistsException e) {
            // Assert
            exceptionMsg = e.getMessage();
        }

        // Assert
        assertEquals("Reset password token already exists!", exceptionMsg);
    }

    @Test
    void setResetPasswordTokenAndSendEmail_NotExist_ThrowUserNotFound() {

        String exceptionMsg = "";

        // Arrange
        String email = "does@not.exist";
        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(organiserRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        try {
            resetPasswordService.setResetPasswordTokenAndSendEmail(email);
        } catch (UserNotFoundException e) {
            // Assert
            exceptionMsg = e.getMessage();
        }

        // Assert
        assertEquals("User Not Found: No such user", exceptionMsg);
    }

    @Test
    void checkAndDeleteTokenAndChangePassword_Student_Success() {
        // Arrange
        String email = "student@test.com";
        String resetPasswordToken = "correctToken";
        String newPassword = "newPassword";
        Student student = new Student();
        student.setEmail(email);
        student.setResetPasswordToken(resetPasswordToken);

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));

        // Act
        ResponseEntity<String> response = resetPasswordService
                .checkAndDeleteTokenAndChangePasswordIfCorrectResetPasswordToken(email, resetPasswordToken,
                        newPassword);

        // Assert
        verify(studentRepository).saveAndFlush(student);
        assertNull(student.getResetPasswordToken());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Token is correct. Password reset.", response.getBody());
    }

    @Test
    void checkAndDeleteTokenAndChangePassword_Organiser_Success() {
        // Arrange
        String email = "organiser@test.com";
        String resetPasswordToken = "correctToken";
        String newPassword = "newPassword";
        Organiser organiser = new Organiser();
        organiser.setEmail(email);
        organiser.setResetPasswordToken(resetPasswordToken);

        when(organiserRepository.findByEmail(email)).thenReturn(Optional.of(organiser));

        // Act
        ResponseEntity<String> response = resetPasswordService
                .checkAndDeleteTokenAndChangePasswordIfCorrectResetPasswordToken(email, resetPasswordToken,
                        newPassword);

        // Assert
        verify(organiserRepository).saveAndFlush(organiser);
        assertNull(organiser.getResetPasswordToken());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Token is correct. Password reset.", response.getBody());
    }

    @Test
    void checkAndDeleteTokenAndChangePassword_IncorrectToken_ThrowIllegalArgumentException() {
        // Arrange
        String email = "student@test.com";
        String resetPasswordToken = "incorrectToken";
        String newPassword = "newPassword";
        Student student = new Student();
        student.setEmail(email);
        student.setResetPasswordToken("correctToken");

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            resetPasswordService.checkAndDeleteTokenAndChangePasswordIfCorrectResetPasswordToken(email,
                    resetPasswordToken, newPassword);
        });

        verify(studentRepository, never()).saveAndFlush(student);
    }

    @Test
    void checkAndDeleteTokenAndChangePassword_StudentTokenMissing_ThrowNotExistException() {
        // Arrange
        String email = "student@test.com";
        String resetPasswordToken = "correctToken";
        String newPassword = "newPassword";
        Student student = new Student();
        student.setEmail(email);

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));

        // Act and Assert
        assertThrows(NotExistException.class, () -> {
            resetPasswordService.checkAndDeleteTokenAndChangePasswordIfCorrectResetPasswordToken(email,
                    resetPasswordToken, newPassword);
        });

        verify(studentRepository, never()).saveAndFlush(student);
    }

    @Test
    void checkAndDeleteTokenAndChangePassword_Student_AccountDoesNotExist_ThrowNotExistException() {
        String exceptionMsg = "";
        // Arrange
        String email = "nonexistent@student.com";
        String resetPasswordToken = "correctToken";
        String newPassword = "newPassword";

        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());

        try {
            resetPasswordService.checkAndDeleteTokenAndChangePasswordIfCorrectResetPasswordToken(email,
                    resetPasswordToken, newPassword);
        } catch (NotExistException e) {
            exceptionMsg = e.getMessage();
        }
        // Act and Assert
        assertEquals("Account doesn't exist!", exceptionMsg);

        // Verify that the exception message matches "Account"
        verify(studentRepository, times(1)).findByEmail(email);
    }

    @Test
    void checkAndDeleteTokenAndChangePassword_Organiser_AccountDoesNotExist_ThrowNotExistException() {
        String exceptionMsg = "";
        // Arrange
        String email = "nonexistent@organiser.com";
        String resetPasswordToken = "correctToken";
        String newPassword = "newPassword";

        when(organiserRepository.findByEmail(email)).thenReturn(Optional.empty());

        try {
            resetPasswordService.checkAndDeleteTokenAndChangePasswordIfCorrectResetPasswordToken(email,
                    resetPasswordToken, newPassword);
        } catch (NotExistException e) {
            exceptionMsg = e.getMessage();
        }
        // Act and Assert
        assertEquals("Account doesn't exist!", exceptionMsg);

        // Verify that the exception message matches "Account"
        verify(organiserRepository, times(1)).findByEmail(email);
    }

    @Test
    void checkAndDeleteTokenAndChangePassword_Organiser_ResetPasswordTokenMissing_ThrowNotExistException() {

        String exceptionMsg = "";
        // Arrange
        String email = "organiser@test.com";
        String resetPasswordToken = "correctToken";
        String newPassword = "newPassword";
        Organiser organiser = new Organiser();
        organiser.setEmail(email);

        when(organiserRepository.findByEmail(email)).thenReturn(Optional.of(organiser));

        // Act and Assert
        try {
            resetPasswordService.checkAndDeleteTokenAndChangePasswordIfCorrectResetPasswordToken(email,
                    resetPasswordToken, newPassword);
        } catch (NotExistException e) {
            // Verify that the exception message matches "Reset password token"
            exceptionMsg = e.getMessage();

        }

        assertEquals("Reset password token doesn't exist!", exceptionMsg);
        verify(organiserRepository, times(1)).findByEmail(email);
    }

    @Test
    void checkAndDeleteTokenAndChangePassword_Organiser_WrongToken_ThrowIllegalArgumentException() {
        // Arrange
        String email = "organiser@test.com";
        String resetPasswordToken = "incorrectToken";
        String newPassword = "newPassword";
        Organiser organiser = new Organiser();
        organiser.setEmail(email);
        organiser.setResetPasswordToken("correctToken");

        when(organiserRepository.findByEmail(email)).thenReturn(Optional.of(organiser));

        // Act and Assert
        try {
            resetPasswordService.checkAndDeleteTokenAndChangePasswordIfCorrectResetPasswordToken(email,
                    resetPasswordToken, newPassword);
        } catch (IllegalArgumentException e) {
            // Verify that the exception message matches "Reset password token is incorrect.
            // Please try again."
            assertEquals("Reset password token is incorrect. Please try again.", e.getMessage());
        }

        verify(organiserRepository, times(1)).findByEmail(email);
    }

}
