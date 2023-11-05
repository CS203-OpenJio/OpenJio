package G3.jio.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import G3.jio.DTO.LoginDTO;
import G3.jio.DTO.RegistrationDTO;
import G3.jio.config.jwt.JwtService;
import G3.jio.entities.AuthenticationResponse;
import G3.jio.entities.Organiser;
import G3.jio.entities.Role;
import G3.jio.entities.Student;
import G3.jio.exceptions.FailedRegistrationException;
import G3.jio.exceptions.InvalidUserTypeException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final OrganiserRepository organiserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Authenticates user. If loginDTO is valid.
     * 
     * @param loginDTO
     * @return
     */
    public AuthenticationResponse authenticateUser(LoginDTO loginDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())    
        );

        if (studentRepository.existsByEmail(loginDTO.getEmail())) {
            var user = studentRepository.findByEmail(loginDTO.getEmail()).map(student -> student).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

        } else if (organiserRepository.existsByEmail(loginDTO.getEmail())){
            var user = organiserRepository.findByEmail(loginDTO.getEmail()).map(organiser -> organiser).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        } else {
            throw new UserNotFoundException("No Such User");
        }
    }

    /**
     * Registers a given User based on UserType
     * 
     * @param registrationDTO
     * @return
     * 
     */
    public AuthenticationResponse registerUser(RegistrationDTO registrationDTO) {
        if (registrationDTO.getUserType() == 'S') {
            return this.registerStudent(registrationDTO);
        } else if (registrationDTO.getUserType() == 'O') {
            return this.registerOrganiser(registrationDTO);
        }
        throw new InvalidUserTypeException("User type is invalid!");
    }

    /**
     * Helper method to register Organiser
     * 
     * @param registrationDTO
     * @return
     */
    private AuthenticationResponse registerOrganiser(RegistrationDTO registrationDTO) {

        // Check if email is already in use
        if (studentRepository.existsByEmail(registrationDTO.getEmail())
                || organiserRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new FailedRegistrationException("Email already taken!");
        }

        Organiser organiser = this.organiserMapToEntity(registrationDTO);

        organiserRepository.save(organiser);
        var jwtToken = jwtService.generateToken(organiser);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();   
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
    private AuthenticationResponse registerStudent(RegistrationDTO registrationDTO) {

        // Check if email is already in use
        if (studentRepository.existsByEmail(registrationDTO.getEmail())
                || organiserRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new FailedRegistrationException("Email already taken!");
        }

        Student student = this.studentMapToEntity(registrationDTO);

        studentRepository.save(student);
        var jwtToken = jwtService.generateToken(student);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    private Student studentMapToEntity(RegistrationDTO registrationDTO) {
        registrationDTO.setPassword(bCryptPasswordEncoder.encode(registrationDTO.getPassword()));
        ModelMapper mapper = new ModelMapper();

        Student student = mapper.map(registrationDTO, Student.class);
        student.setRole(Role.STUDENT);
        return student;
    }

    public Object identifyUser() {

        // read from jwt token
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        if (studentRepository.existsByEmail(email)) {
            return studentRepository.findByEmail(email).orElseThrow();

        } else if (organiserRepository.existsByEmail(email)){
            return organiserRepository.findByEmail(email).orElseThrow();

        } else {
            throw new UserNotFoundException("No Such User");
        }
    }
}
