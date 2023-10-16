package G3.jio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;

class ChangeCredentialServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private OrganiserRepository organiserRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private ChangeCredentialService changeCredentialService;

    @Test
    void replacePasswordForStudent() {
        // Arrange
        String email = "student@example.com";
        String newPassword = "newPassword";
        char userType = 'S';
        Student student = new Student();
        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));

        // Act

        ResponseEntity<String> response = changeCredentialService.replacePassword(email, newPassword, userType);

        // Assert
        verify(bCryptPasswordEncoder).encode(newPassword);
        verify(studentRepository).saveAndFlush(student);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully changed password", response.getBody());
    }

    @Test
    void replacePasswordForOrganiser() {
        // Arrange
        String email = "organiser@example.com";
        String newPassword = "newPassword";
        char userType = 'O';
        Organiser organiser = new Organiser();
        when(organiserRepository.findByEmail(email)).thenReturn(java.util.Optional.of(organiser));

        // Act
        ResponseEntity<String> response = changeCredentialService.replacePassword(email, newPassword, userType);

        // Assert
        verify(bCryptPasswordEncoder).encode(newPassword);
        verify(organiserRepository).saveAndFlush(organiser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully changed password", response.getBody());
    }

    @Test
    void replacePasswordInvalidUserType() {
        // Arrange
        String email = "user@example.com";
        String newPassword = "newPassword";
        char userType = 'X'; // Invalid user type

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> changeCredentialService.replacePassword(email, newPassword, userType));
        assertEquals("Invalid user type", exception.getMessage());

        // Verify that repository methods are not called
        verifyNoInteractions(studentRepository);
        verifyNoInteractions(organiserRepository);
    }
}
