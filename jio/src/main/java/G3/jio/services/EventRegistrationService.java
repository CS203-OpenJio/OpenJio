package G3.jio.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import G3.jio.DTO.EventRegistrationDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Student;
import G3.jio.exceptions.AlreadyExistsException;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.exceptions.NotExistException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.EventRegistrationRepository;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.StudentRepository;

@Service
public class EventRegistrationService {

    @Autowired
    private final EventRegistrationRepository eventRegistrationRepository;
    private final StudentRepository studentRepository;
    private final EventRepository eventRepository;

    public EventRegistrationService(EventRegistrationRepository eventRegistrationRepository,
            StudentRepository studentRepository, EventRepository eventRepository) {
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.studentRepository = studentRepository;
        this.eventRepository = eventRepository;
    }

    // add
    public EventRegistration addEventRegistration(EventRegistrationDTO newEventRegistrationDTO) throws NotExistException {

        // // testing
        // System.out.println(newEventRegistrationDTO.getEventId());
        // System.out.println(newEventRegistrationDTO.getStudentId());
        // System.out.println(newEventRegistrationDTO.isDeregistered());
        // System.out.println(newEventRegistrationDTO.isSuccessful());

        // find student and event
        Long studentId = newEventRegistrationDTO.getStudentId();
        Long eventId = newEventRegistrationDTO.getEventId();
        if (!studentRepository.existsById(studentId)) {
            throw new NotExistException("Student");
        } else if (!eventRepository.existsById(eventId)) {
            throw new NotExistException("Event");
        }

        if (eventRegistrationRepository.existsByStudentIdAndEventId(studentId, eventId)) {
            throw new AlreadyExistsException("Event Registration");
        }

        Student student = studentRepository.getReferenceById(newEventRegistrationDTO.getStudentId());
        Event event = eventRepository.getReferenceById(newEventRegistrationDTO.getEventId());

        // create new entry
        EventRegistration newEventRegistration = new EventRegistration(student, event,
                newEventRegistrationDTO.isDeregistered(), newEventRegistrationDTO.isSuccessful());

        // save into student and event list
        student.addEventRegistration(newEventRegistration);
        event.addEventRegistration(newEventRegistration);
        // System.out.println(newEventRegistration);

        // save into db
        return eventRegistrationRepository.save(newEventRegistration);
    }

    // get registrations by student id
    public List<EventRegistration> getEventRegistrationsByStudentId(long studentId) {
        return eventRegistrationRepository.findAllByStudentId(studentId);
    }

    // get registrations by eventId
    public List<EventRegistration> getEventRegistrationsByEventId(long eventId) {
        return eventRegistrationRepository.findAllByEventId(eventId);
    }

    // delete
    public void deleteEventRegistration(Long id) {
        if (!eventRegistrationRepository.existsById(id)) {
            throw new NotExistException("Registration");
        }

        EventRegistration eventRegistration = eventRegistrationRepository.getReferenceById(id);
        Student student = eventRegistration.getStudent();
        Event event = eventRegistration.getEvent();
        student.getRegistrations().remove(eventRegistration);
        event.getRegistrations().remove(eventRegistration);
        eventRegistrationRepository.deleteById(id);
    }
}
