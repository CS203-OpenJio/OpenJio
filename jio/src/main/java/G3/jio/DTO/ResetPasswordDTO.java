package G3.jio.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetPasswordDTO {

    @NotBlank
    String email;

    /*
     * To be included in the checking method; i.e. only after customer has typed in
     * their reset password token
     */
    @Size(min = 16, max = 16, message = "Token must be of size 16")
    String resetPasswordToken;

    String newPassword;
}
