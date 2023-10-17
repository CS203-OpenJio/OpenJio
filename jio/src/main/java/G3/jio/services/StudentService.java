package G3.jio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import G3.jio.DTO.QueryDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Student;
import G3.jio.exceptions.NotExistException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.StudentRepository;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // list all students
    public List<Student> findAllStudents() {
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
        return studentRepository.findAllByName(name);
    }

    // save a student
    public Student addStudent(Student newStudent) {
        return studentRepository.save(newStudent);
    }

    // update student
    public Student updateStudent(Long id, Student newStudentInfo) {
        Optional<Student> o = studentRepository.findById(id);
        if (!o.isPresent()) {
            throw new UserNotFoundException("Student does not exist!");
        }
        Student student = o.get();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(newStudentInfo, student);
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

    public List<Event> getEventByStudentEmailAndEventRegistrationStatus(QueryDTO queryDTO) {
        Student student = getStudentByEmail(queryDTO.getEmail());

        List<EventRegistration> registrations = student.getRegistrations();
        List<Event> events = new ArrayList<>();

        for (EventRegistration registeredEvent : registrations) {

            if (registeredEvent.getStatus() == queryDTO.getStatus() || queryDTO.getStatus() == null) {
                events.add(registeredEvent.getEvent());
            }
        }

        return events;
    }
}
