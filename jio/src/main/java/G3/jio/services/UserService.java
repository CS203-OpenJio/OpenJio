package G3.jio.services;

import java.util.List;

import G3.jio.entities.User;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.UserRepository;

public class UserService implements Service {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // list all users
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // get user by id
    public User getUser(Long userId) {
        return userRepository.findById(userId).map(user -> {
            return user;
        }).orElse(null);
    }

    // get by name
    public List<User> getUsersByName(String name) {
        return userRepository.findAllByName(name);
    }

    // save a user
    public User addUser(User newUser) {
        return userRepository.save(newUser);
    }

    // update user
    // not sure what we need to update yet
    public User updateUser(Long id, User newUserInfo) {
        return userRepository.findById(id).map(user -> {
            // user.setAccType(newUserInfo.getAccType());
            // user.setDob(newUserInfo.getDob());
            // user.setEmail(newUserInfo.getEmail());
            return userRepository.save(user);
        }).orElse(null);
    }

    // delete by id
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }
}
