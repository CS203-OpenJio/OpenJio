package G3.jio.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRegistrationDTO {
    private long studentId;
    private long eventId;
    private boolean isDeregistered;
    private boolean isSuccessful;
}
