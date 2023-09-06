package G3.jio.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Event {
    @Id
    private Long eventId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Integer capacity;
    private String eventType;
    private String venue;

    public Event(Long eventId, String name, LocalDate startDate, LocalDate endDate, String description,
            Integer capacity, String eventType, String venue) {
        this.eventId = eventId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.capacity = capacity;
        this.eventType = eventType;
        this.venue = venue;
    }
}
