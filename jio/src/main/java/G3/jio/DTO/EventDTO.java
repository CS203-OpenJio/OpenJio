package G3.jio.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "startDate cannot be blank")
    private String startDateTime;

    @NotBlank(message = "endDate cannot be blank")
    private String endDateTime;

    private String venue;
    private String image;
    private int capacity;
    private String algo;
    private String description;

    @Builder.Default
    private boolean isVisible = true;

    private Long organiserId;
    private int minScore;
}
