package G3.jio.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private OrganiserRepository organiserRepository;
    private StudentRepository studentRepository;

    public UserDetailsService(OrganiserRepository organiserRepository, StudentRepository studentRepository) {
        this.organiserRepository = organiserRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {

        Optional<Organiser> tempOrganiser = organiserRepository.findByEmail(email);
        Optional<Student> tempStudent = studentRepository.findByEmail(email);

        Organiser organiser = null;
        Student student = null;
        UserDetails user = null;

        if (tempOrganiser.isEmpty() && tempStudent.isEmpty()) {
            throw new UserNotFoundException(email);
        } else if (!tempOrganiser.isEmpty()) {
            organiser = tempOrganiser.get();
        } else if (!tempStudent.isEmpty()) {
            student = tempStudent.get();
        }

        if (organiser != null) {
            user = User.withUsername(organiser.getEmail()).password(organiser.getPassword()).authorities("USER")
                    .build();
        } else if (student != null) {
            user = User.withUsername(student.getEmail()).password(student.getPassword()).authorities("USER").build();
        }

        return user;
    }
}