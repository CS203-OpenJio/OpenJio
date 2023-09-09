package G3.jio.entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "registerEvent")
public class RegisterEvent {

    @Id
    @Column(name = "eventID")
    private int eventID;

    @Id
    @Column(name = "userID")
    private int userID;

    @Column(name = "isDeregistered")
    private boolean isDeregistered;


    @Column(name = "isSuccessful")
    private boolean isSuccessful;
}
