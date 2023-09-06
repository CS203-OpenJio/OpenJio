package G3.jio.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.entities.User;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.services.UserService;

@RestController
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/v1/users")
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/api/v1/users/{userId}")
    public User getUser(@PathVariable Long userId) {

        // throw exception if user now found
        if (userService.getUser(userId) == null) {
            throw new UserNotFoundException(userId);
        }

        return userService.getUser(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/users")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/api/v1/users/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User newUser) {

        // throw exception if user now found
        if (userService.getUser(userId) == null) {
            throw new UserNotFoundException(userId);
        }

        return userService.updateUser(userId, newUser);
    }
}
