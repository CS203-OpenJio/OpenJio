package G3.jio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.DTO.LoginDTO;
import G3.jio.DTO.SignUpDTO;
import G3.jio.services.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Account Successfully Created"),
        @ApiResponse(responseCode = "400", description = "Input fields invalid")
    })
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignUpDTO signUpDTO) {
        // if (studentRepository.existsByEmail(signUpDTO.getEmail())) {
        //     return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        // }



        // // create user object
        // Student student = new Student(
        //     signUpDTO.getName(),
        //     signUpDTO.getEmail(),
        //     passwordEncoder.encode(signUpDTO.getPassword()),
        //     signUpDTO.getMatricNo(),
        //     signUpDTO.getPhone()
        // );
        // student.setRole(Role.USER);
        // studentRepository.save(student);
        
        Object responseObject = authService.registerUser(signUpDTO);

        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);

    }
}
