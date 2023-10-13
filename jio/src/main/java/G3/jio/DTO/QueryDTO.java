package G3.jio.DTO;

import G3.jio.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryDTO {
    String name;
    Long id;
    String email;
    String algo;
    Status status;
}
