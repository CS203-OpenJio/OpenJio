package G3.jio.entities;

import java.time.LocalDate;
import java.util.Locale.Category;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "students")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "image")
    private String image;

    @Column(name = "accType")
    private char accType;

    @Column(name = "matricNo")
    private String matricNo;

    @Column(name = "phone")
    private String phone;

    @Column(name = "dob")
    private LocalDate dob;

    @OneToMany(mappedBy = "student")
    Set<EventRegistration> registrations = new HashSet<>();

    public void addEventRegistration(EventRegistration eventRegistration) {
        this.registrations.add(eventRegistration);
    }
}
