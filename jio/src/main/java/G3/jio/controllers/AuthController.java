package G3.jio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.DTO.LoginDTO;
import G3.jio.DTO.RegistrationDTO;
import G3.jio.entities.AuthenticationResponse;
import G3.jio.services.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account Logged in Successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect Username or Password")
    })
    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {

        return ResponseEntity.ok(authService.authenticateUser(loginDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account Successfully Created"),
            @ApiResponse(responseCode = "400", description = "Input fields invalid")
    })
    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {

        // return 201
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(registrationDTO));
    }

    @GetMapping(path = "/identify")
    public ResponseEntity<Object> identifyUser() {

        return ResponseEntity.ok(authService.identifyUser());
    }
}
