package G3.jio.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.DTO.ResetPasswordDTO;
import G3.jio.services.ResetPasswordService;

@RestController
@RequestMapping("api/v1/forgot-password")
public class ResetPasswordController {
    ResetPasswordService resetPasswordService;

    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    // @ApiOperation("Gets the reset password token for a user")
    @PostMapping("/token")
    public ResponseEntity<String> setResetPasswordTokenAndSendEmail(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return resetPasswordService.setResetPasswordTokenAndSendEmail(resetPasswordDTO.getEmail());
    }

    // @ApiOperation("checks the reset password token for a user, deleting if a
    // match is found")
    @PostMapping("/")
    public ResponseEntity<String> checkAndDeleteTokenAndChangePasswordIfCorrectResetPasswordToken(
            @RequestBody ResetPasswordDTO resetPasswordDTO) {

        /*
         * will throw exceptions if the check fails
         * if check passes, it will clear the resetPasswordToken field and then reset
         * the password
         */
        return resetPasswordService.checkAndDeleteTokenAndChangePasswordIfCorrectResetPasswordToken(
                resetPasswordDTO.getEmail(),
                resetPasswordDTO.getResetPasswordToken(), resetPasswordDTO.getNewPassword());
    }
}
