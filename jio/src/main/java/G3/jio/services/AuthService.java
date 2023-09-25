package G3.jio.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import G3.jio.DTO.SignUpDTO;
import G3.jio.config.Role;
import G3.jio.entities.Student;
import G3.jio.exceptions.FailedRegistrationException;
import G3.jio.repositories.StudentRepository;

@Service
public class AuthService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Object registerUser(SignUpDTO signUpDTO) {
        
        // check if email is taken
        if (studentRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new FailedRegistrationException("Email already taken!");
        }

        Student student = this.studentMapToEntity(signUpDTO);
        student.setRole(Role.USER);

        return studentRepository.save(student);
    }

    private Student studentMapToEntity(SignUpDTO signUpDTO) {
        signUpDTO.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        ModelMapper mapper = new ModelMapper();

        Student student = mapper.map(signUpDTO, Student.class);
        return student;
    }
}
