package G3.jio.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRegistrationDTO {
    private Long studentId;
    private Long eventId;
    private boolean isDeregistered;
    private boolean isSuccessful;

    // public EventRegistrationDTO(Long studentId, Long eventId, boolean isDeregistered, boolean isSuccessful) {
    //     this.studentId = studentId;
    //     this.eventId = eventId;
    //     this.isDeregistered = isDeregistered;
    //     this.isSuccessful = isSuccessful;
    // }
}
