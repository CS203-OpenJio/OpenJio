package G3.jio.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetPasswordMailerDTO {

    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email cannot be blank")
    private String toEmail;

    @Size(min = 16, max = 16)
    @NotBlank(message = "Token cannot be blank")
    private String resetPasswordToken;
}
