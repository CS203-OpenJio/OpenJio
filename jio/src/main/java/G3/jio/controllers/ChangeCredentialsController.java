package G3.jio.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.DTO.ChangeCredentialsDTO;
import G3.jio.DTO.LoginDTO;
import G3.jio.services.AuthService;
import G3.jio.services.ChangeCredentialService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/edit-profile")
@RequiredArgsConstructor
public class ChangeCredentialsController {

    private final ChangeCredentialService changeCredentialService;
    private final AuthService authService;

    // @ApiOperation(value = "Updates password of a Customer or a Merchant", notes =
    // "New username must not already be used")
    @Operation(summary = "Updates password")
    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangeCredentialsDTO changeCredentialsDTO) {
        verifyLogin(changeCredentialsDTO);

        return changeCredentialService.replacePassword(changeCredentialsDTO.getEmail(),
                changeCredentialsDTO.getNewPassword(), changeCredentialsDTO.getUserType());
    }

    @Operation(summary = "Verifies login")
    public void verifyLogin(ChangeCredentialsDTO changeCredentialsDTO) {
        LoginDTO loginDTO = new LoginDTO(changeCredentialsDTO.getEmail(), changeCredentialsDTO.getPassword());
        authService.authenticateUser(loginDTO);
    }
}
 