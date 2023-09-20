package G3.jio.services;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import G3.jio.entities.Student;
import G3.jio.repositories.StudentRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private StudentRepository studentRepository;

    public UserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public UserDetails loadUserByUsername(String username) {
        Optional<Student> tempStudent = studentRepository.findByName(username);

        Student student = null;
        UserDetails user = null;

        if (tempStudent.isEmpty()) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        } else if (!tempStudent.isEmpty()){
            student = tempStudent.get();
        }

        if (student != null){
            user = User.withUsername(student.getUsername()).password(student.getPassword()).authorities("USER").build();
        }
        
        return user;
    }
}