package G3.jio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Status;
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
        return studentRepository.findById(studentId).map(student -> {
            return student;
        }).orElse(null);
    }

    // get student by email
    public Student getStudentByEmail(String email) {
        Student s = studentRepository.findByEmail(email).map(student -> {
            return student;
        }).orElse(null);

        if (s == null) {
            throw new NotExistException("Student");
        } else {
            return s;
        }
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
            throw new UserNotFoundException();
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
            throw new UserNotFoundException();
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

    public List<Event> getEventByStudentEmailAndEventRegistrationStatus(String email, Status status) {
        Student student = getStudentByEmail(email);

        List<EventRegistration> registrations = student.getRegistrations();
        List<Event> events = new ArrayList<>();

        for (EventRegistration registeredEvent : registrations) {

            if (registeredEvent.getStatus() == status) {
                events.add(registeredEvent.getEvent());
            }
        }

        return events;
    }
}
