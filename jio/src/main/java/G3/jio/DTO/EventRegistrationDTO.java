package G3.jio.DTO;

import G3.jio.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRegistrationDTO {
    private Long studentId;
    private Long eventId;
    private Status status;
    private boolean isPresentForEvent;
    private boolean isCompleted;
}
