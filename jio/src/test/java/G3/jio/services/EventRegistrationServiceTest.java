package G3.jio.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import G3.jio.DTO.EventRegistrationDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Status;
import G3.jio.entities.Student;
import G3.jio.repositories.EventRegistrationRepository;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.StudentRepository;

public class EventRegistrationServiceTest {
        
    @Mock
    private EventRegistrationRepository eventRegistrationRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private StudentService studentService;

    private Student student;
    private Event event;
    private EventRegistrationDTO eventRegistrationDTO;

    @InjectMocks
    EventRegistrationService eventRegistrationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        student = new Student();
        student.setName("student");
        student.setEmail("student@test.com");
        student.setId(1L);

        event = new Event();
        event.setName("event1");
        event.setCapacity(1);
        event.setId(1L);
        event.setMinScore(0);

        eventRegistrationDTO = new EventRegistrationDTO();
        eventRegistrationDTO.setEventId(1L);
        eventRegistrationDTO.setStudentId(1L);
    }

    @Test
    void addEventRegistration_NoStudentIdGiven_Success_ReturnEventRegistration() {

        // arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        EventRegistration newEventRegistration = new EventRegistration();
        newEventRegistration.setStudent(student);
        newEventRegistration.setEvent(event);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(student);
        when(studentService.getStudentByEmail(student.getEmail())).thenReturn(student);
        when(eventRepository.existsById(student.getId())).thenReturn(true);
        when(eventRegistrationRepository.existsByStudentIdAndEventId(student.getId(), event.getId())).thenReturn(false);
        when(eventRepository.getReferenceById(event.getId())).thenReturn(event);
        when(eventRegistrationRepository.save(any(EventRegistration.class))).thenReturn(newEventRegistration);
        
        // act
        eventRegistrationDTO.setStudentId(null);
        EventRegistration er = eventRegistrationService.addEventRegistration(eventRegistrationDTO);

        // assert
        assertNotNull(er);
        assertEquals(er.getStudent(), newEventRegistration.getStudent());
        assertEquals(er.getEvent(), newEventRegistration.getEvent());

        verify(eventRegistrationRepository).save(any(EventRegistration.class));
    }

    @Test
    void addEventRegistration_StudentIdGiven_Success_ReturnEventRegistration() {

        // arrange
        EventRegistration newEventRegistration = new EventRegistration();
        newEventRegistration.setStudent(student);
        newEventRegistration.setEvent(event);

        when(studentRepository.existsById(eventRegistrationDTO.getStudentId())).thenReturn(true);
        when(studentRepository.getReferenceById(eventRegistrationDTO.getStudentId())).thenReturn(student);

        // make sure event exists
        when(eventRepository.existsById(student.getId())).thenReturn(true);

        // check if only 1 sign up
        when(eventRegistrationRepository.existsByStudentIdAndEventId(student.getId(), event.getId())).thenReturn(false);

        // retrieve event
        when(eventRepository.getReferenceById(event.getId())).thenReturn(event);
        when(eventRegistrationRepository.save(any(EventRegistration.class))).thenReturn(newEventRegistration);
        
        // act
        EventRegistration er = eventRegistrationService.addEventRegistration(eventRegistrationDTO);

        // assert
        assertNotNull(er);
        assertEquals(er.getStudent(), newEventRegistration.getStudent());
        assertEquals(er.getEvent(), newEventRegistration.getEvent());
        verify(eventRegistrationRepository).save(any(EventRegistration.class));
    }

    @Test
    void addEventRegistration_EventNotFound_Failure_ThrowEventNotFoundException() {

        // arrange
        EventRegistration newEventRegistration = new EventRegistration();
        newEventRegistration.setStudent(student);
        newEventRegistration.setEvent(event);

        when(studentRepository.existsById(eventRegistrationDTO.getStudentId())).thenReturn(true);
        when(studentRepository.getReferenceById(eventRegistrationDTO.getStudentId())).thenReturn(student);

        // make sure event exists
        when(eventRepository.existsById(student.getId())).thenReturn(false);
        
        // act
        String exceptionMsg = "";
        try {
            eventRegistrationService.addEventRegistration(eventRegistrationDTO);
        } catch (Exception e) {
            exceptionMsg = e.getMessage();
        }

        // assert
        assertEquals("Event does not exist!", exceptionMsg);
    }

    @Test
    void deleteEventRegistration_Success() {

        // arrange
        EventRegistration er = new EventRegistration(1L, student, event, Status.PENDING,
                                true, false, LocalDateTime.now());

        List<EventRegistration> studentRegistrations = new ArrayList<>();
        studentRegistrations.add(er);
        student.setRegistrations(studentRegistrations);

        List<EventRegistration> eventRegistrations = new ArrayList<>();
        eventRegistrations.add(er);
        event.setRegistrations(eventRegistrations);

        when(eventRegistrationRepository.existsById(er.getId())).thenReturn(true);
        when(eventRegistrationRepository.getReferenceById(er.getId())).thenReturn(er);
        
        // act
        eventRegistrationService.deleteEventRegistration(er.getId());

        // assert
        List<Event> emptyList = new ArrayList<>();
        assertEquals(emptyList, studentRegistrations);
        assertEquals(emptyList, eventRegistrations);
        verify(eventRegistrationRepository).deleteById(er.getId());
    }

    @Test
    void updateEventRegistration_Success() {
        // arrange
        EventRegistration er = new EventRegistration(1L, student, event, Status.PENDING,
                                true, false, LocalDateTime.now());
        when(eventRegistrationRepository.getReferenceByEventIdAndStudentId(eventRegistrationDTO.getEventId(), eventRegistrationDTO.getStudentId())).thenReturn(er);
        
        // act
        eventRegistrationDTO.setPresentForEvent(false);
        eventRegistrationDTO.setCompleted(true);
        eventRegistrationDTO.setStatus(Status.ACCEPTED);
        eventRegistrationService.updateEventRegistration(eventRegistrationDTO);

        // assert
        assertEquals(eventRegistrationDTO.isPresentForEvent(), er.isPresentForEvent());
        assertEquals(eventRegistrationDTO.isCompleted(), eventRegistrationDTO.isCompleted());
        assertEquals(eventRegistrationDTO.getStatus(), er.getStatus());
    }
}
