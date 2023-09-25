package G3.jio.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpDTO {

    @NotNull
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotNull
    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @Size(min = 8, message = "Password must be between 8 and 50 characters")
    private String password;

    @NotNull
    @Size(min = 8, max = 8, message = "Matriculation number must be valid")
    private String matricNo;

    @NotNull
    @Size(min = 8, max = 8, message = "Phone number must be valid")
    private String phone;
}
