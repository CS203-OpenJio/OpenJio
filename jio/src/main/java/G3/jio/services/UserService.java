package G3.jio.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import G3.jio.entities.Event;
import G3.jio.entities.User;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.UserRepository;

@Service
public class UserService {

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
        Optional<User> o = userRepository.findById(id);
        if (!o.isPresent()) {
            throw new UserNotFoundException();
        }
        User user = o.get();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(newUserInfo, user);
        userRepository.saveAndFlush(user);

        return user;
    }

    // delete by id
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }
}
