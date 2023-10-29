package G3.jio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;

public class UserDetailsServiceTest {

    @Mock
    OrganiserRepository organiserRepository;

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    UserDetailsService userDetailsService;

    Student student;

    Organiser organiser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        student = new Student();
        student.setEmail("student@test.com");
        student.setPassword("password");

        organiser = new Organiser();
        organiser.setEmail("organiser@test.com");
        organiser.setPassword("password");

    }

    @Test
    void loadUserByUsername_StudentExists_ReturnUserDetails() {

        // Arrange
        when(studentRepository.findByEmail(any(String.class))).thenReturn(Optional.of(student));
        when(organiserRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        // Act
        UserDetails user = userDetailsService.loadUserByUsername(student.getEmail());
        UserDetails expectedUser = User.withUsername(student.getEmail()).password(student.getPassword())
                .authorities("USER")
                .build();

        // Assert
        assertEquals(user, expectedUser);
        verify(studentRepository).findByEmail(student.getEmail());
    }

    @Test
    void loadUserByUsername_OrganiserExists_ReturnUserDetails() {

        // Arrange
        when(organiserRepository.findByEmail(any(String.class))).thenReturn(Optional.of(organiser));
        when(studentRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        // Act
        UserDetails user = userDetailsService.loadUserByUsername(organiser.getEmail());
        UserDetails expectedUser = User.withUsername(organiser.getEmail()).password(organiser.getPassword())
                .authorities("USER")
                .build();

        // Assert
        assertEquals(user, expectedUser);
        verify(studentRepository).findByEmail(organiser.getEmail());
    }

    @Test
    void loadUserByUsername_StudentDoesNotExist_ThrowUserNotFoundException() {

        String exceptionMsg = "";

        // Arrange
        when(studentRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        // Act
        try {
            UserDetails user = userDetailsService.loadUserByUsername(student.getEmail());
        } catch (UserNotFoundException e) {
            exceptionMsg = e.getMessage();
        }

        // Assert
        assertEquals("User Not Found: " + student.getEmail(), exceptionMsg);
        verify(studentRepository).findByEmail(student.getEmail());
    }

    @Test
    void loadUserByUsername_OrganiserDoesNotExist_ThrowUserNotFoundException() {

        String exceptionMsg = "";

        // Arrange
        when(organiserRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        // Act
        try {
            UserDetails user = userDetailsService.loadUserByUsername(organiser.getEmail());
        } catch (UserNotFoundException e) {
            exceptionMsg = e.getMessage();
        }

        // Assert
        assertEquals("User Not Found: " + organiser.getEmail(), exceptionMsg);
        verify(organiserRepository).findByEmail(organiser.getEmail());
    }

}
