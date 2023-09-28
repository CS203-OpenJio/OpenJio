package G3.jio.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.entities.Event;
import G3.jio.entities.Student;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.StudentRepository;
import G3.jio.services.StudentService;
import jakarta.validation.Valid;

@RestController
@Controller
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private StudentRepository studentRepository; // TODO: may need to remove
    private BCryptPasswordEncoder encoder;

    @Autowired
    public StudentController(StudentService studentService, StudentRepository studentRepository,
            BCryptPasswordEncoder encoder) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.encoder = encoder;
    }

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // get student given their id
    @GetMapping(path = "/id/{id}")
    public Student getStudentById(@PathVariable("id") Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            throw new UserNotFoundException(" " + id);
        }

        return student;
    }

    // get student given their email
    @GetMapping(path = "/email/{email}")
    public Student getStudentByEmail(@PathVariable("email") String email) {
        Student student = studentService.getStudentByEmail(email);
        if (student == null) {
            throw new UserNotFoundException(" " + email);
        }

        return student;
    }

    // get events registered for by student email
    @GetMapping(path = "/email/{email}/registered")
    public List<Event> getRegisteredEventsByStudentId(@PathVariable String email) {

        return studentService.getEventByStudentEmail(email);
    }

    // get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAllStudents();
    }

    // not working
    // get all students with name
    @GetMapping(path = "/name/{name}")
    public List<Student> getStudentsByName(@PathVariable String name) {
        name = name.replaceAll("%20", " ");
        return studentService.getStudentsByName(name);
    }

    // add a student
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Student addStudent(@Valid @RequestBody Student student) {
        student.setPassword(encoder.encode(student.getPassword()));
        return studentService.addStudent(student);
    }

    // delete student
    @DeleteMapping(path = "/id/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    // update student with the id
    @PutMapping(path = "/id/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }
}
