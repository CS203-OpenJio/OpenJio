package G3.jio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserInterest {

    private String category;
    private Long userId;
}
