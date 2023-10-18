package G3.jio.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.DTO.QueryDTO;
import G3.jio.DTO.StudentDTO;
import G3.jio.entities.Event;
import G3.jio.entities.Student;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.services.StudentService;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;

    // get student given their id
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            throw new UserNotFoundException(" " + id);
        }

        return ResponseEntity.ok(student);
    }

    // get student given their email
    @PostMapping(path = "/email")
    public ResponseEntity<Student> getStudentByEmail(@RequestBody QueryDTO queryDTO) {

        return ResponseEntity.ok(studentService.getStudentByEmail(queryDTO.getEmail()));
    }

    // get events registered for by student email and status
    @PostMapping(path = "/email/events")
    public ResponseEntity<List<Event>> getEventByStudentEmailAndEventRegistrationStatus(@RequestBody QueryDTO queryDTO) {

        return ResponseEntity.ok(studentService.getEventByStudentEmailAndEventRegistrationStatus(queryDTO));
    }

    // get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // get all students with name
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<List<Student>> getStudentsByName(@PathVariable String name) {
        name = name.replaceAll("%20", " ");
        return ResponseEntity.ok(studentService.getStudentsByName(name));
    }

    // // add a student ---> If uncommented need to add private final BCryptPasswordEncoder encoder; variable
    // @PostMapping
    // public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
    //     student.setPassword(encoder.encode(student.getPassword()));
    //     return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(student));
    // }

    // delete student
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted");
    }

    // update student with the id
    @PutMapping(path = "/id/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
    }
}
