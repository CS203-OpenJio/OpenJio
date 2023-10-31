package G3.jio.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import G3.jio.entities.AuthenticationResponse;
import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.exceptions.AlreadyExistsException;
import G3.jio.exceptions.NotExistException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResetPasswordService {

    private final StudentRepository studentRepository;
    private final OrganiserRepository organiserRepository;
    private final ChangeCredentialService changeCredentialService;
    private final MailService mailService;

    public ResponseEntity<String> setResetPasswordTokenAndSendEmail(String email) {

        if (studentRepository.existsByEmail(email)) {
            Student student = studentRepository.findByEmail(email).map(s -> s).orElseThrow();

            if (student.getResetPasswordToken() != null)
                throw new AlreadyExistsException("Reset password token");

            student.setResetPasswordToken(RandomString.make(16));
            studentRepository.saveAndFlush(student);
            mailService.sendSimpleMessage(email, "OpenJio Password Reset", student.getResetPasswordToken());

        } else if (organiserRepository.existsByEmail(email)){
            Organiser organiser = organiserRepository.findByEmail(email).map(o -> o).orElseThrow();
            if (organiser.getResetPasswordToken() != null)
                throw new AlreadyExistsException("Reset password token");

            organiser.setResetPasswordToken(RandomString.make(16));
            organiserRepository.saveAndFlush(organiser);

            mailService.sendSimpleMessage(email, "OpenJio Password Reset", organiser.getResetPasswordToken());

        } else {
            throw new UserNotFoundException("No Such User");
        }

        return new ResponseEntity<>("An email has been sent to you. Please check it for a code to reset your password!",
                HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> checkAndDeleteTokenAndChangePasswordIfCorrectResetPasswordToken(String email,
            String resetPasswordToken, String newPassword) {

        Student student = null;
        Organiser organiser = null;
        boolean isStudent;

        Optional<Student> optionalStudent = studentRepository.findByEmail(email);
        if (optionalStudent.isPresent()) {
            student = optionalStudent.get();
            isStudent = true;
        } else {
            organiser = organiserRepository.findByEmail(email)
                    .orElseThrow(() -> new NotExistException("Account"));
            isStudent = false;
        }

        /*
         * we delete the reset password token here if it exists
         */
        if (isStudent) {
            if (student.getResetPasswordToken() == null)
                throw new NotExistException("Reset password token");

            // wrong token
            if (!student.getResetPasswordToken().equals(resetPasswordToken))
                throw new IllegalArgumentException("Reset password token is incorrect. Please try again.");

            student.setResetPasswordToken(null);
            studentRepository.saveAndFlush(student);
        } else {
            if (organiser.getResetPasswordToken() == null)
                throw new NotExistException("Reset password token");

            // wrong token
            if (!organiser.getResetPasswordToken().equals(resetPasswordToken))
                throw new IllegalArgumentException("Reset password token is incorrect. Please try again.");

            organiser.setResetPasswordToken(null);
            organiserRepository.saveAndFlush(organiser);
        }

        /*
         * we reset the user's password here
         */

        resetUserPassword(isStudent, isStudent ? student : organiser, newPassword);

        return new ResponseEntity<>("Token is correct. Password reset.", HttpStatus.OK);
    }

    private void resetUserPassword(boolean isStudent, Object user, String newPassword) {
        Student student = null;
        Organiser organiser = null;

        if (isStudent) {
            student = (Student) user;
            changeCredentialService.replacePassword(student.getEmail(), newPassword, 'S');
        } else {
            organiser = (Organiser) user;
            changeCredentialService.replacePassword(organiser.getEmail(), newPassword, 'O');
        }
    }
}
