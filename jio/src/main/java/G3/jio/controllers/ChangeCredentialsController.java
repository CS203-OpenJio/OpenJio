package G3.jio.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.DTO.ChangeCredentialsDTO;
import G3.jio.DTO.LoginDTO;
import G3.jio.services.AuthService;
import G3.jio.services.ChangeCredentialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "api/v1/edit-profile")
@RequiredArgsConstructor
public class ChangeCredentialsController {

    private final ChangeCredentialService changeCredentialService;
    private final AuthService authService;

    // @ApiOperation(value = "Updates password of a Customer or a Merchant", notes =
    // "New username must not already be used")
    @Operation(summary = "Updates password of a Customer or a Merchant")
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

    // ********************* NOTE: We wil not allow username to be changed
    // *********************
    // @ApiOperation(value = "Updates username of a Customer or a Merchant", notes =
    // "New username must not already be used")
    // @ApiResponses(value = {
    // @ApiResponse(code = 200, message = "Username successfully changed to
    // {username}"),
    // @ApiResponse(code = 400, message = "Input fields invalid or username already
    // taken") })
    // @PutMapping("/username")
    // public ResponseEntity<String> changeUsername(@Valid @RequestBody
    // ChangeCredentialsDTO changeCredentialsDTO) {
    // verifyLogin(changeCredentialsDTO);

    // String newUsername = changeCredentialsDTO.getNewUsername();
    // if (newUsername == null || newUsername.isBlank())
    // throw new IllegalArgumentException("New username should not be blank.");

    // changeCredentialService.validateNewUsername(newUsername,
    // changeCredentialsDTO.getUserType());

    // return
    // changeCredentialService.replaceUsername(changeCredentialsDTO.getUsername(),
    // newUsername,
    // changeCredentialsDTO.getUserType());
    // }
}
