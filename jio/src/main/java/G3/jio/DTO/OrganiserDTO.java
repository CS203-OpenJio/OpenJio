package G3.jio.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganiserDTO {
    
    private String name;
    private String email;
    private String password;
    private String description;
}
