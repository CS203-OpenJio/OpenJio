package G3.jio.controllers;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
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

import G3.jio.entities.Student;
import G3.jio.services.StudentService;
import G3.jio.exceptions.UserNotFoundException;

@RestController
@Controller
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;

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
    public Student addStudent(@RequestBody Student student) {
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
