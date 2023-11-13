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

import G3.jio.DTO.CustomResponseDTO;
import G3.jio.DTO.QueryDTO;
import G3.jio.DTO.StudentDTO;
import G3.jio.entities.Student;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "api/v1")
public class StudentController {

    private final StudentService studentService;

    // get student given their id
    @Operation(summary = "Get student given their id")
    @GetMapping(path = "/students/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            throw new UserNotFoundException(" " + id);
        }

        return ResponseEntity.ok(student);
    }

    // get student given their email
    @Operation(summary = "Get student given their email")
    @PostMapping(path = "/students/email")
    public ResponseEntity<Student> getStudentByEmail(@RequestBody QueryDTO queryDTO) {

        return ResponseEntity.ok(studentService.getStudentByEmail(queryDTO.getEmail()));
    }

    // get events registered for by student email and status
    @Operation(summary = "Get events registered by student given their email and event registration status")
    @PostMapping(path = "/students/email/events")
    public ResponseEntity<List<CustomResponseDTO>> getEventByStudentEmailAndEventRegistrationStatus(
            @RequestBody QueryDTO queryDTO) {

        return ResponseEntity.ok(studentService.getEventByStudentEmailAndEventRegistrationStatus(queryDTO));
    }

    // get all students
    @Operation(summary = "Get all students")
    @GetMapping(path = "/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // get all students with name
    @Operation(summary = "Get students given their name")
    @GetMapping(path = "/students?name={name}")
    public ResponseEntity<List<Student>> getStudentsByName(@PathVariable String name) {
        name = name.replaceAll("%20", " ");
        return ResponseEntity.ok(studentService.getStudentsByName(name));
    }

    // delete student
    @Operation(summary = "Delete student by Id")
    @DeleteMapping(path = "/students/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted");
    }

    // update student with the id
    @Operation(summary = "Update student by Id")
    @PutMapping(path = "/students/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
    }
}
