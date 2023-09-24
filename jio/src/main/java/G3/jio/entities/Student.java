package G3.jio.entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import G3.jio.config.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "students")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Student implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Column(name = "name")
    private String name;

    @NotNull
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(min = 8, message = "Password must be between 8 and 50 characters")
    @Column(name = "password")
    private String password;

    @Column(name = "image")
    private String image;

    @Column(name = "accType")
    private char accType;

    @NotNull
    @Size(min = 8, max = 8, message = "Matriculation number must be valid")
    @Column(name = "matricNo")
    private String matricNo;

    @NotNull
    @Size(min = 8, max = 8, message = "Phone number must be valid")
    @Column(name = "phone")
    private String phone;

    // @NotNull
    @Column(name = "dob")
    private LocalDate dob;

    @OneToMany(mappedBy = "student")
    Set<EventRegistration> registrations = new HashSet<>();

    public void addEventRegistration(EventRegistration eventRegistration) {
        this.registrations.add(eventRegistration);
    }

    public Student(String name, String username, String password, String matricNo, String phone, LocalDate dob,
            Role role) {
        this.name = name;
        this.email = username;
        this.password = password;
        this.matricNo = matricNo;
        this.phone = phone;
        this.dob = dob;
        this.role = role;
    }

    // security

    @Column(name = "role")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // email in our case
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
