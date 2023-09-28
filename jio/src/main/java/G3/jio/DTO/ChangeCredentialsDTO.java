package G3.jio.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeCredentialsDTO {

    private String newPassword;

    // @Size(min = 5, max = 15, message = "Username must be between 5 and 15
    // characters long")
    @NotBlank(message = "Username cannot be blank")
    String email;

    @NotBlank(message = "Password cannot be blank")
    String password;

    @NotNull(message = "Usertype cannot be blank")
    private Character userType;

    // STUDENTS
    private String matricNo;
    private String phone;

    // ORGANISERS
    private String description;
}
