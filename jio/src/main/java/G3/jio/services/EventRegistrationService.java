package G3.jio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import G3.jio.DTO.EventRegistrationDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Student;
import G3.jio.exceptions.NotExistException;
import G3.jio.repositories.EventRegistrationRepository;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.StudentRepository;

@Service
public class EventRegistrationService {

    @Autowired
    private final EventRegistrationRepository eventRegistrationRepository;
    private final StudentRepository studentRepository;
    private final EventRepository eventRepository;

    public EventRegistrationService(EventRegistrationRepository eventRegistrationRepository, StudentRepository studentRepository, EventRepository eventRepository) {
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.studentRepository = studentRepository;
        this.eventRepository = eventRepository;
    }

    // add
    public EventRegistration addEventRegistration(EventRegistrationDTO newEventRegistrationDTO) {
        Student student = studentRepository.getReferenceById(newEventRegistrationDTO.getStudentId());
        Event event = eventRepository.getReferenceById(newEventRegistrationDTO.getEventId());
        EventRegistration newEventRegistration = new EventRegistration(student, event, newEventRegistrationDTO.isDeregistered(), newEventRegistrationDTO.isSuccessful());
        return eventRegistrationRepository.save(newEventRegistration);
    }

    // get registrations by studentId
    public List<EventRegistration> getEventRegistrationsByStudentId(long studentId) {
        return eventRegistrationRepository.findAllByStudentId(studentId);
    }

    // get registrations by eventId
    public List<EventRegistration> getEventRegistrationsByEventId(long eventId) {
        return eventRegistrationRepository.findAllByEventId(eventId);
    }

    //delete
    public void deleteEventRegistration(Long id) {
        if (!eventRegistrationRepository.existsById(id)) {
            throw new NotExistException("Registration");
        }
        eventRegistrationRepository.deleteById(id);
    }
}
