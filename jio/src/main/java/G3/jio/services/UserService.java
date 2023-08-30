package G3.jio.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import G3.jio.entities.User;

@Service
public class UserService implements G3.jio.services.Service{
    
    private ArrayList<User> users = new ArrayList<>();
    
    public UserService() {
        users.add(new User(1L, "john weak", "123", "john.weak@gmail.com", 12345678, LocalDate.of(2001,1,1)));
        users.add(new User(2L, "bobby bimbo", "123", "bim.bo@gmail.com", 23456789, LocalDate.of(2001,1,2)));
        users.add(new User(3L, "black spideman", "123", "bl.ack@gmail.com", 54326789, LocalDate.of(2001,1,3)));
        users.add(new User(4L, "amoh gus", "123", "amo.gus@gmail.com", 78431234, LocalDate.of(2001,1,4)));
    }

    public List<User> listUsers() {
        return users;
    }

    public User getUser(Long userId){
        for(User user : users){
            if(user.getUserId().equals(userId))
                return user;
        }

        return null;
    }

    public User addUser(User user) {
        users.add(user);
        return user;
    }
}
