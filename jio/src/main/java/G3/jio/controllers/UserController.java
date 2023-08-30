package G3.jio.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.entities.User;
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
    public User getUser(Long userId) {
        return userService.getUser(userId);
    }
}
