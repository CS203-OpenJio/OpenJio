package G3.jio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.DTO.LoginDTO;
import G3.jio.DTO.RegistrationDTO;
import G3.jio.services.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account Logged in Successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect Username or Password")
    })
    @PostMapping(path = "/login")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
        ResponseEntity<String> response = authService.authenticateUser(loginDTO);
        return response;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account Successfully Created"),
            @ApiResponse(responseCode = "400", description = "Input fields invalid")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {

        Object responseObject = authService.registerUser(registrationDTO);

        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

}
