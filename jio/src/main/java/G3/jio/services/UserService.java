package G3.jio.services;

import java.util.List;

import G3.jio.entities.User;
import G3.jio.repositories.UserRepository;

public class UserService implements Service {
    
    private UserRepository users;

    public UserService(UserRepository users) {
        this.users = users;
    }

    // list all users
    public List<User> listUsers() {
        return users.findAll();
    }

    // get user by id
    public User getUser(Long userId) {
        return users.findById(userId).map(user -> {
            return user;
        }).orElse(null);
    }

    // save a user
    public User addUser(User newUser) {
        return users.save(newUser);
    }

    // update user
    // not sure what we need to update yet
    public User updateUser(Long id, User newUserInfo){
        return users.findById(id).map(user -> {
            // user.setAccType(newUserInfo.getAccType());
            // user.setDob(newUserInfo.getDob());
            // user.setEmail(newUserInfo.getEmail());
            return users.save(user);
        }).orElse(null);
    }

    public void deleteUser(long id) {
        users.deleteById(id);
    }
}

