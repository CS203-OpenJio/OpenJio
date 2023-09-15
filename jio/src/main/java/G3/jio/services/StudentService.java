package G3.jio.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    // get student by id
    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId).map(student -> {
            return student;
        }).orElse(null);
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
}
