package G3.jio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import G3.jio.DTO.CustomResponseDTO;
import G3.jio.DTO.QueryDTO;
import G3.jio.DTO.StudentDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Student;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.StudentRepository;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // list all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // get student by id
    public Student getStudent(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (!optionalStudent.isPresent()) {
            throw new UserNotFoundException("Student does not exist!");
        }

        return optionalStudent.get();
    }

    // get student by email
    public Student getStudentByEmail(String email) {
        Optional<Student> optionalStudent = studentRepository.findByEmail(email);

        if (!optionalStudent.isPresent()) {
            throw new UserNotFoundException("Student does not exist!");
        }

        return optionalStudent.get();
    }

    // get by name
    public List<Student> getStudentsByName(String name) {
        if (!studentRepository.existsByName(name)) {
            throw new UserNotFoundException("Student does not exist!");
        }
        return studentRepository.findAllByName(name);
    }

    // // save a student
    // public Student addStudent(Student newStudent) {
    // return studentRepository.save(newStudent);
    // }

    // update student
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        Optional<Student> o = studentRepository.findById(id);
        if (!o.isPresent()) {
            throw new UserNotFoundException("Student does not exist!");
        }
        Student student = o.get();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(studentDTO, student);
        studentRepository.saveAndFlush(student);

        return student;
    }

    // delete by id
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new UserNotFoundException("Student does not exist!");
        }
        studentRepository.deleteById(id);
    }

    // get events by student email
    // TODO
    public List<Event> getEventByStudentEmail(String email) {

        Student student = getStudentByEmail(email);

        List<EventRegistration> registrations = student.getRegistrations();
        List<Event> events = new ArrayList<>();

        for (EventRegistration registeredEvent : registrations) {
            events.add(registeredEvent.getEvent());
        }

        return events;
    }

    public List<CustomResponseDTO> getEventByStudentEmailAndEventRegistrationStatus(QueryDTO queryDTO) {
        Student student = getStudentByEmail(queryDTO.getEmail());

        List<EventRegistration> registrations = student.getRegistrations();
        List<CustomResponseDTO> events = new ArrayList<>();

        System.out.println("---------------------");

        for (EventRegistration registeredEvent : registrations) {

            if (registeredEvent.getStatus() == queryDTO.getStatus() || queryDTO.getStatus() == null) {
                Event event = registeredEvent.getEvent();
                CustomResponseDTO customResponse = new CustomResponseDTO();
                customResponse.setEventName(event.getName());
                customResponse.setVenue(event.getVenue());
                customResponse.setStartDateTime(event.getStartDateTime());
                customResponse.setEndDateTime(event.getEndDateTime());
                customResponse.setStatus(registeredEvent.getStatus());
                customResponse.setCompleted(event.isCompleted());
                events.add(customResponse);
            }
        }

        return events;
    }
}
