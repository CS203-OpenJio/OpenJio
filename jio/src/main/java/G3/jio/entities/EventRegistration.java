package G3.jio.entities;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

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
import lombok.ToString.Exclude;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "event_registration")
public class EventRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    @JsonBackReference
    // @Exclude
    Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    @JsonBackReference
    // @Exclude
    Event event;

    @Column(name = "isAccepted")
    private boolean isAccepted = false;


    @Column(name = "isSuccessful")
    private boolean isSuccessful = false;


    public EventRegistration(Student student, Event event, boolean isAccepted, boolean isSuccessful) {
        this.student = student;
        this.event = event;
        this.isAccepted = isAccepted;
        this.isSuccessful = isSuccessful;
    }

    @JsonView
    public Long getSid() {
        return student.getId();
    }
    
    @JsonView
    public Long getEid() {
        return event.getId();
    }
}
