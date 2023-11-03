package G3.jio.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/")
    public String health() {
        return "OK";
    }

    @PostMapping("/")
    public String handlePost() {
        return "POST request handled";
    }
}
