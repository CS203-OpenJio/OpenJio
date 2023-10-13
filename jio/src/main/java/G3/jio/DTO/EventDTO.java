package G3.jio.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private String name;
    private String startDateTime;
    private String endDateTime;
    private String venue;
    private String image;
    private int capacity;
    private int algo;
    private String description;
    private boolean isVisible;
    private Long organiserId;
    private int minScore;
}
