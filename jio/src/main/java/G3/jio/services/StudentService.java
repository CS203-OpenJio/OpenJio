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

    /**
     * Get all students
     * 
     * @return
     */
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Get student by id
     * 
     * @param studentId
     * @return
     */
    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId).map(student -> {
            return student;
        }).orElse(null);
    }

    /**
     * Get student by email
     * 
     * @param email
     * @return
     */
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

    /**
     * Get student by name
     * 
     * @param name
     * @return
     */
    public List<Student> getStudentsByName(String name) {
        return studentRepository.findAllByName(name);
    }

    /**
     * Add student
     * 
     * @param newStudent
     */
    public Student addStudent(Student newStudent) {
        return studentRepository.save(newStudent);
    }

    /**
     * Update existing student of given id with details from newStudentInfo
     * 
     * @param id
     * @param newStudentInfo
     * @return
     */
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

    /**
     * Delete student of given id
     * 
     * @param id
     */
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        studentRepository.deleteById(id);
    }

    /**
     * Get all events registered by student
     * 
     * @param email
     * @return
     */
    public List<Event> getEventByStudentEmail(String email) {

        Student student = getStudentByEmail(email);

        List<EventRegistration> registrations = student.getRegistrations();
        List<Event> events = new ArrayList<>();

        for (EventRegistration registeredEvent : registrations) {
            events.add(registeredEvent.getEvent());
        }

        return events;
    }

    /**
     * Get all events registered by student with given status
     * 
     * @param email
     * @param status
     * @return
     */
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
