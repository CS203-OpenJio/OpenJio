package G3.jio.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private String image;
    private String phone;
    private LocalDate dob;
    private String matricNo;

    // impt to be Integer
    private Integer smuCreditScore;
}
