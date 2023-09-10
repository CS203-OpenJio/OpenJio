package G3.jio.entities;

import java.util.List;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "eventName")
    private String event_name;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "venue")
    private String venue;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "isRegistered")
    private boolean isRegistered;

    @Column(name = "algoType")
    private int algo;

    @Column(name = "description")
    private String description;

    @Column(name = "isVisible")
    private boolean isVisible;

    private List<User> listOfusers;

}
