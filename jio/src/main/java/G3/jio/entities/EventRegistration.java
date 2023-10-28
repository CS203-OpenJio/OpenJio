package G3.jio.entities;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "event_registration")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class EventRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id")
    @JsonBackReference(value = "student-registration")
    Student student;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_id")
    @JsonBackReference(value = "event-registration")
    Event event;

    @Column(name = "Status")
    private Status status = Status.PENDING;

    @Column(name = "attended_event")
    private boolean isPresentForEvent = true;

    @Column(name = "completed")
    private boolean isCompleted = false;

    @Column(name = "time")
    private final LocalDateTime time = LocalDateTime.now();

    @JsonIgnore
    @Column(name = "timeCompleted")
    private LocalDateTime timeCompleted;

    @JsonView
    public Long getSid() {
        return student.getId();
    }
    
    @JsonView
    public Long getEid() {
        return event.getId();
    }

    @JsonIgnore
    public int getStudentScore() {
        return student.getSmuCreditScore();
    }

    @JsonIgnore
    public double getEventScore() {
        int signUps = event.getRegistrations().size();
        double score = signUps / event.getCapacity();
        double minEventScore = 0.3;
        double maxEventScore = 3.0;
        return Math.min(Math.max(minEventScore, score), maxEventScore);
    }

    @Override
    public String toString() {
        return "EventRegistration [id=" + id + ", status=" + status + ", isPresentForEvent=" + isPresentForEvent
                + ", isCompleted=" + isCompleted + "]";
    }
}
