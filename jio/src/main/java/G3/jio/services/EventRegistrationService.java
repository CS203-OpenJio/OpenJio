package G3.jio.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import G3.jio.DTO.EventRegistrationDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Student;
import G3.jio.exceptions.AlreadyExistsException;
import G3.jio.exceptions.FailedRegistrationException;
import G3.jio.exceptions.NotExistException;
import G3.jio.repositories.EventRegistrationRepository;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventRegistrationService {

    @Autowired
    private final EventRegistrationRepository eventRegistrationRepository;
    private final StudentRepository studentRepository;
    private final EventRepository eventRepository;
    private final StudentService studentService;

    // add
    public EventRegistration addEventRegistration(EventRegistrationDTO newEventRegistrationDTO) throws NotExistException {


        Student student = null;
        Long studentId = newEventRegistrationDTO.getStudentId();
        if (studentId == null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String studentEmail = userDetails.getUsername();
            student = studentService.getStudentByEmail(studentEmail);

        } else if (!studentRepository.existsById(studentId)) {
            throw new NotExistException("Student");

        } else {
            student = studentRepository.getReferenceById(studentId);
        }

        // find event
        Long eventId = newEventRegistrationDTO.getEventId();
        if (!eventRepository.existsById(eventId)) {
            throw new NotExistException("Event");
        }

        // check if exists
        if (eventRegistrationRepository.existsByStudentIdAndEventId(student.getId(), eventId)) {
            throw new AlreadyExistsException("Event Registration");
        }

        Event event = eventRepository.getReferenceById(newEventRegistrationDTO.getEventId());

        // check if student score is > min score of event
        if (student.getSmuCreditScore() < event.getMinScore()) {
            throw new FailedRegistrationException("Student's score is too low");
        }

        // create new entry
        EventRegistration newEventRegistration = new EventRegistration();
        newEventRegistration.setStudent(student);
        newEventRegistration.setEvent(event);

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

    public EventRegistration getEventRegistrationsByEventIdAndStudentId(Long eventId, Long studentId) {
        EventRegistration eventRegistration = eventRegistrationRepository.getReferenceByEventIdAndStudentId(eventId, studentId);
        if (eventRegistration == null) {
            throw new NotExistException("Event Registration");
        }

        return eventRegistration;
    }

    // update registration status and attendance
    public EventRegistration updateEventRegistration(EventRegistrationDTO eventRegistrationDTO) {
        EventRegistration eventRegistration = getEventRegistrationsByEventIdAndStudentId(eventRegistrationDTO.getEventId(), eventRegistrationDTO.getStudentId());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
        mapper.map(eventRegistrationDTO, eventRegistration);
        return eventRegistrationRepository.saveAndFlush(eventRegistration);
    }
}
