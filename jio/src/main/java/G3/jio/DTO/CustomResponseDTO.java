package G3.jio.DTO;

import java.time.LocalDateTime;

import G3.jio.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomResponseDTO {
    String eventName;
    String venue;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    Status status;
    boolean completed;
}
