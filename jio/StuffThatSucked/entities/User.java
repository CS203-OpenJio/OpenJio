package G3.jio.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    private Long userId;
    private String name;
    private String password;
    private String email;
    private Integer phone;
    private LocalDate dob;
    
    public User(Long userId, String name, String password, String email, Integer phone, LocalDate dob) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
    }
}
