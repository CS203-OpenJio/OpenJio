package G3.jio.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import G3.jio.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
// @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CustomResponseDTO {
    String eventName;
    String venue;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    Status status;
    boolean completed;
}
