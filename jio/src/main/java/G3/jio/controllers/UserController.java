package G3.jio.controllers;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.entities.User;
import G3.jio.services.UserService;
import G3.jio.exceptions.UserNotFoundException;

@RestController
@Controller
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // get user given their id
    @GetMapping(path = "/id/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new UserNotFoundException(" " + id);
        }

        return user;
    }

    // get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    // not working
    // get all users with name
    @GetMapping(path = "/name/{name}")
    public List<User> getUsersByName(@PathVariable String name) {
        name = name.replaceAll("%20", " ");
        return userService.getUsersByName(name);
    }

    // add a user
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // delete user
    @DeleteMapping(path = "/id/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // update user with the id
    @PutMapping(path = "/id/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
}
