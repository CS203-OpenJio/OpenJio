package G3.jio.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Status;
import G3.jio.entities.Student;
import G3.jio.repositories.EventRegistrationRepository;

public class AlgoServiceTest {
    
    @Mock
    private EventRegistrationRepository eventRegistrationRepository;

    @InjectMocks
    private AlgoService algoService;

    Event event;

    Student student1;
    Student student2;
    Student student3;
    Student student4;
    Student student5;

    EventRegistration eventRegistration1;
    EventRegistration eventRegistration2;
    EventRegistration eventRegistration3;
    EventRegistration eventRegistration4;
    EventRegistration eventRegistration5;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        event = createEvent(1L, "test event!!!!", 1, 0);
        student1 = createStudent(1L, "student1", "student1@test.com", "password");
        student2 = createStudent(2L, "student2", "student2@test.com", "password");
        student3 = createStudent(3L, "student3", "student3@test.com", "password");
        student4 = createStudent(4L, "student4", "student4@test.com", "password");
        student5 = createStudent(5L, "student5", "student5@test.com", "password");

        try {
            eventRegistration1 = createEventRegistration(1L, student1, event);
            Thread.sleep(50);
            eventRegistration2 = createEventRegistration(2L, student2, event);
            Thread.sleep(50);
            eventRegistration3 = createEventRegistration(3L, student3, event);
            Thread.sleep(50);
            eventRegistration4 = createEventRegistration(4L, student4, event);
            Thread.sleep(50);
            eventRegistration5 = createEventRegistration(5L, student5, event);  
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    void allocateSlotsForEventFCFS_Success() {
        
        // Arrange
        event.setCapacity(2);

        // Act
        List<EventRegistration> result = algoService.allocateSlotsForEventFCFS(event);
        
        // Assert
        assertEquals(true, result.get(0).getTime().isBefore(result.get(1).getTime()));
        assertEquals(eventRegistration1, result.get(0));
        assertEquals(eventRegistration2, result.get(1));
        assertEquals(2, result.size());

        verify(eventRegistrationRepository).saveAndFlush(result.get(0));
        verify(eventRegistrationRepository).saveAndFlush(result.get(1));
    }

    @Test
    void allocateSlotsForEventRandom_Success() {

        // Act
        int count = 0;
        for (int i = 0; i < 500; i++) {
            List<EventRegistration> result = algoService.allocateSlotsForEventRandom(event);
            if (result.get(0).equals(eventRegistration1) || result.get(0).equals(eventRegistration5)) {
                count++;
            }
        }

        double percent = (double) count / 10; 

        // assert
        assertEquals(20, percent, 3);
    }

    @Test
    void allocateSlotsForEventWeightedRandom_Success() {

        // arrange
        createFakeEventRegistration(6L, student1, event, true);
        createFakeEventRegistration(7L, student1, event, false);
        createFakeEventRegistration(8L, student1, event, false);
        createFakeEventRegistration(9L, student1, event, false);
        createFakeEventRegistration(10L, student1, event, false);

        createFakeEventRegistration(11L, student2, event, true);
        createFakeEventRegistration(12L, student2, event, true);
        createFakeEventRegistration(13L, student2, event, false);
        createFakeEventRegistration(14L, student2, event, false);
        createFakeEventRegistration(15L, student2, event, false);

        createFakeEventRegistration(16L, student3, event, true);
        createFakeEventRegistration(17L, student3, event, true);
        createFakeEventRegistration(18L, student3, event, true);
        createFakeEventRegistration(19L, student3, event, false);
        createFakeEventRegistration(20L, student3, event, false);

        createFakeEventRegistration(21L, student4, event, true);
        createFakeEventRegistration(22L, student4, event, true);
        createFakeEventRegistration(23L, student4, event, true);
        createFakeEventRegistration(24L, student4, event, true);
        createFakeEventRegistration(25L, student4, event, false);

        // Act
        double count1 = 0;
        double count2 = 0;
        double count3 = 0;
        double count4 = 0;
        double count5 = 0;
        for (int i = 0; i < 1000; i++) {
            List<EventRegistration> result = algoService.allocateSlotsForEventWeightedRandom(event);
            if (result.get(0).equals(eventRegistration1)) {
                count1++;
            } else if (result.get(0).equals(eventRegistration2)) {
                count2++;
            } else if (result.get(0).equals(eventRegistration3)) {
                count3++;
            } else if (result.get(0).equals(eventRegistration4)) {
                count4++;
            } else if (result.get(0).equals(eventRegistration5)) {
                count5++;
            }
        }

        // assert
        double total = student1.getSmuCreditScore() + student2.getSmuCreditScore() + student3.getSmuCreditScore() + student4.getSmuCreditScore() + student5.getSmuCreditScore();
        assertEquals(student1.getSmuCreditScore()/total * 100, count1/10, 5 );
        assertEquals(student2.getSmuCreditScore()/total * 100, count2/10, 5 );
        assertEquals(student3.getSmuCreditScore()/total * 100, count3/10, 5 );
        assertEquals(student4.getSmuCreditScore()/total * 100, count4/10, 5 );
        assertEquals(student5.getSmuCreditScore()/total * 100, count5/10, 5 );
    }

    @Test
    void allocateSlotsForEventScore_Success() {
        
        // arrange
        createFakeEventRegistration(6L, student1, event, true);
        createFakeEventRegistration(7L, student1, event, false);
        createFakeEventRegistration(8L, student1, event, false);
        createFakeEventRegistration(9L, student1, event, false);
        createFakeEventRegistration(10L, student1, event, false);

        createFakeEventRegistration(11L, student2, event, true);
        createFakeEventRegistration(12L, student2, event, true);
        createFakeEventRegistration(13L, student2, event, false);
        createFakeEventRegistration(14L, student2, event, false);
        createFakeEventRegistration(15L, student2, event, false);

        createFakeEventRegistration(16L, student3, event, true);
        createFakeEventRegistration(17L, student3, event, true);
        createFakeEventRegistration(18L, student3, event, true);
        createFakeEventRegistration(19L, student3, event, false);
        createFakeEventRegistration(20L, student3, event, false);

        createFakeEventRegistration(21L, student4, event, true);
        createFakeEventRegistration(22L, student4, event, true);
        createFakeEventRegistration(23L, student4, event, true);
        createFakeEventRegistration(24L, student4, event, true);
        createFakeEventRegistration(25L, student4, event, false);

        // act
        event.setCapacity(3);
        List<EventRegistration> result = algoService.allocateSlotsForEventScore(event);

        // assert
        assertEquals(eventRegistration5, result.get(0));
        assertEquals(eventRegistration4, result.get(1));
        assertTrue(result.get(0).getStudentScore() > result.get(1).getStudentScore());
        assertTrue(result.get(1).getStudentScore() > result.get(2).getStudentScore());
        assertTrue(result.get(0).getStudentScore() > result.get(2).getStudentScore());
    } 

        

    public Student createStudent(Long id, String name, String email, String password) {

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setEmail(email);
        student.setPassword(password);
        return student;
    }

    public Event createEvent(Long id, String name, int capacity, int minScore) {

        event = new Event();
        event.setId(id);
        event.setName(name);
        event.setCapacity(capacity);
        event.setMinScore(minScore);

        return event;
    }

    public EventRegistration createEventRegistration(Long id, Student student, Event event) {

        EventRegistration er = new EventRegistration();
        er.setId(id);
        er.setStudent(student);
        er.setEvent(event);
        er.setPresentForEvent(true);

        student.addEventRegistration(er);
        event.addEventRegistration(er);

        return er;
    }


    public EventRegistration createFakeEventRegistration(Long id, Student student, Event event, boolean present) {

        EventRegistration er = new EventRegistration();
        er.setId(id);
        er.setStudent(student);
        er.setEvent(event);
        er.setPresentForEvent(present);
        er.setCompleted(true);
        er.setStatus(Status.ACCEPTED);

        student.addEventRegistration(er);

        return er;
    }
}
