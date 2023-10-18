package G3.jio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import G3.jio.entities.Student;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private OrganiserRepository organiserRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_AllStudents_Success_ReturnAllStudents() {

        // arrange
        Student student1 = new Student();
        student1.setEmail("student1@test.com");
        Student student2 = new Student();
        student2.setEmail("student2@test.com");
        Student student3 = new Student();
        student3.setEmail("student3@test.com");
        Student student4 = new Student();
        student4.setEmail("student4@test.com");
        List<Student> studentList = List.of(student1, student2, student3, student4);

        when(studentRepository.findAll()).thenReturn(studentList);

        // act
        List<Student> responseList = studentService.getAllStudents();

        // assert
        verify(studentRepository, times(1)).findAll();
        assertEquals(studentList, responseList);

    }

    @Test
    void findAll_NoStudents_Success_ReturnEmptyList(){

        // arrange
        when(studentRepository.findAll()).thenReturn(new ArrayList<Student>());

        // act 
        List<Student> responseList = studentService.getAllStudents();
        
        //assert
        verify(studentRepository, times(1)).findAll();
        assertEquals(responseList, new ArrayList<Student>());
        
    }

    @Test
    void getStudentById_Exist_Success() {

        // Arrange
        Student student = new Student();
        student.setId(1L);
        student.setEmail("test@test.com");
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        // Act
        Student responseStudent = studentService.getStudent(student.getId());

        // Assert
        assertEquals(student, responseStudent);
        verify(studentRepository, times(1)).findById(student.getId());
    }

    @Test
    void getStudentById_NotFound_Failure_ThrowsUserNotFound() {
        String exceptionMsg = "";
        // arrange
        Student student = new Student();
        student.setId(1L);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // act and assert
        try {
            studentService.getStudent(student.getId());
        } catch (UserNotFoundException e) {
            exceptionMsg = e.getMessage();
        }

        // assert
        verify(studentRepository, times(1)).findById(any(Long.class));
        assertEquals(exceptionMsg, "User Not Found: Student does not exist!");
    }

    @Test
    void getStudentByEmail_Exist_Success_ReturnStudent() {

        // arrange
        Student student = new Student();
        student.setEmail("test@test.com");
        ;

        Optional<Student> optionalStudent = Optional.of(student);
        when(studentRepository.findByEmail(any(String.class))).thenReturn(optionalStudent);

        // act
        Student responseStudent = studentService.getStudentByEmail(student.getEmail());

        // assert
        assertEquals(responseStudent, student);
        verify(studentRepository, times(1)).findByEmail(student.getEmail());

    }

    @Test
    void getStudentByEmail_NotFound_Failure_ThrowUserNotFound() {

        String exceptionMsg = "";
        // arrange
        Student student = new Student();
        student.setEmail("test@test.com");

        when(studentRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        // act and assert
        try {
            studentService.getStudentByEmail(student.getEmail());
        } catch (UserNotFoundException e) {
            exceptionMsg = e.getMessage();
        }

        // assert
        verify(studentRepository, times(1)).findByEmail(any(String.class));
        assertEquals(exceptionMsg, "User Not Found: Student does not exist!");

    }

    @Test
    void deleteStudent_StudentExists_Success() {

        // Arrange
        String name = "Daniel";
        String email = "test@test.com";
        Long id = 1L;
        Student originalStudent = new Student();
        originalStudent.setId(1L);
        originalStudent.setName(name);
        originalStudent.setEmail(email);

        when(studentRepository.existsById(any(Long.class))).thenReturn(true);

        // Act
        studentService.deleteStudent(originalStudent.getId());

        // Assert
        verify(studentRepository, times(1)).existsById(id);
        verify(studentRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteStudent_StudentNotExist_Failure_ThrowUserNotFound() {

        // Arrange
        String exceptionMsg = "";
        String name = "Daniel";
        String email = "test@test.com";
        Long id = 1L;
        Student originalStudent = new Student();
        originalStudent.setId(1L);
        originalStudent.setName(name);
        originalStudent.setEmail(email);

        when(studentRepository.existsById(any(Long.class))).thenReturn(false);

        // Act
        try {
            studentService.deleteStudent(id);
        } catch (UserNotFoundException e) {
            exceptionMsg = e.getMessage();
        }

        // Assert
        assertEquals("User Not Found: Student does not exist!", exceptionMsg);
        verify(studentRepository, times(1)).existsById(id);
    }

    /*********************
     * (WIP) We currently do not allow changes to email and name
     *********************/

    // @Test
    // void testUpdateStudent_StudentExists_ReturnStudent() {

    // // Arrange
    // //UpdateStudentDetailsDTO updateStudentDetailsDTO = new
    // UpdateStudentDetailsDTO("Jacky", "jacky@yahoo.com.sg", null, 1000);
    // Student newStudent = new Student();
    // newStudent.setName("Daniel");
    // newStudent.setEmail("newemail@test.com");
    // Student originalStudent = new Student();
    // originalStudent.setId(1L);
    // originalStudent.setName(newStudent.getEmail());
    // originalStudent.setEmail(newStudent.getEmail());

    // when(studentRepository.existsByEmail(any(String.class)))
    // .thenReturn(false);
    // when(studentRepository.findById(any(Long.class)))
    // .thenReturn(Optional.of(originalStudent));
    // when(studentRepository.findByEmail(anyString()))
    // .thenReturn(Optional.of(newStudent));
    // when(studentRepository.saveAndFlush(any(Student.class)))
    // .thenReturn(newStudent);

    // // Act
    // Student responseStudent =
    // studentService.updateStudent(originalStudent.getId(), newStudent);

    // // Assert
    // assertEquals(originalStudent, responseStudent);
    // verify(studentRepository, times(1)).existsByEmail(newStudent.getEmail());
    // verify(studentRepository, times(1)).findByEmail("newemail@test.com");
    // verify(studentRepository, times(1)).findById(originalStudent.getId());
    // verify(studentRepository, times(1)).saveAndFlush(originalStudent);

    // }

    // @Test
    // void
    // testUpdateStudent_EmailAlreadyExistsInStudent_ThrowAlreadyExistsException() {

    // // Arrange
    // String email = "Daniel";
    // UpdateStudentDetailsDTO updateStudentDetailsDTO = new
    // UpdateStudentDetailsDTO("Jacky", "jacky@yahoo.com.sg", null, 1000);
    // String exceptionMsg = "";

    // when(organiserRepository.existsByEmail(anyString()))
    // .thenReturn(false);
    // when(studentRepository.existsByEmail(anyString()))
    // .thenReturn(true);

    // // Act
    // try {
    // Student responseStudent = studentService.updateStudent(email,
    // updateStudentDetailsDTO);
    // } catch (AlreadyExistsException e) {
    // exceptionMsg = e.getMessage();
    // }

    // // Assert
    // assertEquals("Email already exists!", exceptionMsg);
    // verify(organiserRepository,
    // times(1)).existsByEmail(updateStudentDetailsDTO.getEmail());
    // verify(studentRepository,
    // times(1)).existsByEmail(updateStudentDetailsDTO.getEmail());
    // }
}