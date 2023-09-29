package G3.jio.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private LocalDate startDate;
    private LocalDate endDate;
    private String venue;
    private String image;
    private int capacity;
    private int algo;
    private String description;
    private boolean isVisible;
    private Long organiserId;
    private int minScore;
}
