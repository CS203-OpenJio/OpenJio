package G3.jio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.exceptions.AlreadyExistsException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;

@Service
public class ChangeCredentialService {

    StudentRepository studentRepository;
    OrganiserRepository organiserRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ChangeCredentialService(StudentRepository studentRepository,
            OrganiserRepository organiserRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.studentRepository = studentRepository;
        this.organiserRepository = organiserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<String> replacePassword(String email, String newPassword, Character userType) {
        if (userType == 'S') {
            Student student = studentRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User does not exist"));
            return replaceStudentPassword(student, newPassword);
        } else if (userType == 'O') {
            Organiser organiser = organiserRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User does not exist"));
            return replaceOrganiserPassword(organiser, newPassword);
        } else
            throw new IllegalArgumentException("Invalid user type");
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

    // ******* WILL NOT ALLOW CHANGE OF EMAIL ********
    // public void validateNewEmail(String newEmail, Character userType) {

    // if (!userType.equals('S') && !userType.equals('O')) {
    // throw new IllegalArgumentException("Invalid user type");
    // } else if ((userType.equals('O') &&
    // organiserRepository.existsByEmail(newEmail))
    // || userType.equals('S') && studentRepository.existsByEmail(newEmail)) {
    // throw new AlreadyExistsException("Username");
    // }

    // }

    // public ResponseEntity<String> replaceUsername(String oldUsername, String
    // newUsername, Character userType) {
    // if (userType == 'C') {
    // Student Student = StudentRepository.findByUsername(oldUsername)
    // .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ERROR));
    // return replaceStudentUsername(Student, newUsername);
    // } else {
    // Organiser Organiser = OrganiserRepository.findByUsername(oldUsername)
    // .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ERROR));
    // return replaceOrganiserUsername(Organiser, newUsername);
    // }
    // }

    // public ResponseEntity<String> replaceStudentUsername(Student student, String
    // newUsername) {
    // Student.setUsername(newUsername);
    // StudentRepository.saveAndFlush(Student);
    // return new ResponseEntity<>("Successfully changed username to " +
    // newUsername, HttpStatus.OK);
    // }

    // public ResponseEntity<String> replaceOrganiserUsername(Organiser organiser,
    // String newUsername) {
    // Organiser.setUsername(newUsername);
    // OrganiserRepository.saveAndFlush(organiser);
    // return new ResponseEntity<>("Successfully changed username to " +
    // newUsername, HttpStatus.OK);
    // }
}
