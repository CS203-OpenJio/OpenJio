package G3.jio.entities;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import G3.jio.config.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "students")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Student implements UserDetails {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 5, max = 15, message = "Name must be between 5 and 15 characters long")
    @NotBlank(message = "Name cannot be null")
    @Column(name = "name")
    private String name;

    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email cannot be null")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "image")
    @JsonIgnore
    private String image;

    @Column(name = "accType")
    private char accType;

    // @NotNull
    // @Size(min = 8, max = 8, message = "Matriculation number must be valid")
    @Column(name = "matricNo")
    private String matricNo;

    // @NotNull
    // @Size(min = 8, max = 8, message = "Phone number must be valid")
    @Column(name = "phone")
    private String phone;

    // @NotNull
    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "role")
    public Role role;

    @OneToMany(mappedBy = "student", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    Set<EventRegistration> registrations = new HashSet<>();

    public void addEventRegistration(EventRegistration eventRegistration) {
        this.registrations.add(eventRegistration);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // TODO: temporary constructor to create admin
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

    // **************** SECURITY ****************
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.name()));
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
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
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (image == null) {
            if (other.image != null)
                return false;
        } else if (!image.equals(other.image))
            return false;
        if (accType != other.accType)
            return false;
        if (matricNo == null) {
            if (other.matricNo != null)
                return false;
        } else if (!matricNo.equals(other.matricNo))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (dob == null) {
            if (other.dob != null)
                return false;
        } else if (!dob.equals(other.dob))
            return false;
        if (role != other.role)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + accType;
        result = prime * result + ((matricNo == null) ? 0 : matricNo.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((dob == null) ? 0 : dob.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        return result;
    }

}
