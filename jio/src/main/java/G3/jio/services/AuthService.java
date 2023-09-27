package G3.jio.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import G3.jio.DTO.LoginDTO;
import G3.jio.DTO.RegistrationDTO;
import G3.jio.config.Role;
import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.exceptions.FailedRegistrationException;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;

@Service
public class AuthService {

    private StudentRepository studentRepository;
    private OrganiserRepository organiserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    // private JwtProvider jwtProvider;

    @Autowired
    public AuthService(StudentRepository studentRepository, OrganiserRepository organiserRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager) {
        this.studentRepository = studentRepository;
        this.organiserRepository = organiserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticates user. If loginDTO is valid.
     * 
     * @param loginDTO
     * @return
     */
    public ResponseEntity<String> authenticateUser(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    /**
     * Registers a given User based on UserType
     * 
     * @param registrationDTO
     * @return
     * 
     */
    public Object registerUser(RegistrationDTO registrationDTO) {
        if (registrationDTO.getUserType() == 'S') {
            return this.registerStudent(registrationDTO);
        } else if (registrationDTO.getUserType() == 'O') {
            return this.registerOrganiser(registrationDTO);
        }
        throw new FailedRegistrationException();
    }

    /**
     * Helper method to register Organiser
     * 
     * @param registrationDTO
     * @return
     */
    private Organiser registerOrganiser(RegistrationDTO registrationDTO) {

        // Check if email is already in use
        if (studentRepository.existsByEmail(registrationDTO.getEmail())
                || organiserRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new FailedRegistrationException("Email already taken!");
        }

        Organiser organiser = this.organiserMapToEntity(registrationDTO);

        organiserRepository.save(organiser);

        return organiser;
    }

    private Organiser organiserMapToEntity(RegistrationDTO registrationDTO) {
        registrationDTO.setPassword(bCryptPasswordEncoder.encode(registrationDTO.getPassword()));
        ModelMapper mapper = new ModelMapper();

        Organiser organiser = mapper.map(registrationDTO, Organiser.class);
        organiser.setRole(Role.ORGANISER);
        return organiser;
    }

    /**
     * Helper method to register Student
     * 
     * @param registrationDTO
     * @return
     */
    private Student registerStudent(RegistrationDTO registrationDTO) {

        // Check if email is already in use
        if (studentRepository.existsByEmail(registrationDTO.getEmail())
                || organiserRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new FailedRegistrationException("Email already taken!");
        }

        Student student = this.studentMapToEntity(registrationDTO);

        studentRepository.save(student);

        return student;
    }

    private Student studentMapToEntity(RegistrationDTO registrationDTO) {
        registrationDTO.setPassword(bCryptPasswordEncoder.encode(registrationDTO.getPassword()));
        ModelMapper mapper = new ModelMapper();

        Student student = mapper.map(registrationDTO, Student.class);
        student.setRole(Role.STUDENT);
        return student;
    }
}
