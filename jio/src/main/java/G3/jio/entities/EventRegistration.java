package G3.jio.entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class EventRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;

    @Column(name = "isDeregistered")
    private boolean isDeregistered = false;


    @Column(name = "isSuccessful")
    private boolean isSuccessful = false;


    public EventRegistration(Student student, Event event, boolean isDeregistered, boolean isSuccessful) {
        this.student = student;
        this.event = event;
        this.isDeregistered = isDeregistered;
        this.isSuccessful = isSuccessful;
    }
}
