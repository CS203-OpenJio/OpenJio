package G3.jio.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "eventName")
    private String name;

    @Column(name = "startDateTime")
    private LocalDateTime startDateTime;

    @Column(name = "endDateTime")
    private LocalDateTime endDateTime;

    @Column(name = "venue")
    private String venue;

    @Column(name = "image")
    private String image;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "algoType")
    private int algo;

    @Column(name = "description", length = 65535)
    private String description;

    @Column(name = "isVisible")
    private boolean isVisible;

    @Column(name = "isCompleted")
    private boolean isCompleted;

    @Column(name = "minScore")
    private int minScore;

    // ******************* Relationships *******************

    // with student
    @OneToMany(mappedBy = "event", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    List<EventRegistration> registrations = new ArrayList<>();

    public void addEventRegistration(EventRegistration eventRegistration) {
        this.registrations.add(eventRegistration);
    }

    // with organiser
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organiser_id")
    private Organiser organiser;

    // **************** Equals and hashcode *********************
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (startDateTime == null) {
            if (other.startDateTime != null)
                return false;
        } else if (!startDateTime.equals(other.startDateTime))
            return false;
        if (endDateTime == null) {
            if (other.endDateTime != null)
                return false;
        } else if (!endDateTime.equals(other.endDateTime))
            return false;
        if (venue == null) {
            if (other.venue != null)
                return false;
        } else if (!venue.equals(other.venue))
            return false;
        if (minScore != other.minScore)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((startDateTime == null) ? 0 : startDateTime.hashCode());
        result = prime * result + ((endDateTime == null) ? 0 : endDateTime.hashCode());
        result = prime * result + ((venue == null) ? 0 : venue.hashCode());
        result = prime * result + minScore;
        return result;
    }
}
