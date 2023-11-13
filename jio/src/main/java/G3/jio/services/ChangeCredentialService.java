package G3.jio.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.exceptions.InvalidUserTypeException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangeCredentialService {

    private final StudentRepository studentRepository;
    private final OrganiserRepository organiserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<String> replacePassword(String email, String newPassword, Character userType) {
        if (userType == 'S') {
            Student student = studentRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("Student does not exist!"));
            return replaceStudentPassword(student, newPassword);
        } else if (userType == 'O') {
            Organiser organiser = organiserRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("Organiser does not exist!"));
            return replaceOrganiserPassword(organiser, newPassword);
        } else
            throw new InvalidUserTypeException("User type is invalid!");
    }

    public ResponseEntity<String> replaceStudentPassword(Student student, String newPassword) {
        student.setPassword(bCryptPasswordEncoder.encode(newPassword));
        studentRepository.saveAndFlush(student);
        return new ResponseEntity<>("Successfully changed password", HttpStatus.OK);
    }

    public ResponseEntity<String> replaceOrganiserPassword(Organiser organiser, String newPassword) {
        organiser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        organiserRepository.saveAndFlush(organiser);
        return new ResponseEntity<>("Successfully changed password", HttpStatus.OK);
    }
}
