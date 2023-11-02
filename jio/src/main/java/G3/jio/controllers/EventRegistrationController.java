package G3.jio.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.DTO.EventRegistrationDTO;
import G3.jio.entities.EventRegistration;
import G3.jio.services.EventRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequestMapping(path = "api/v1/register-event")
@RequiredArgsConstructor
@CrossOrigin(origins = "openjio.xyz", allowedHeaders = "*")
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    // get by student or eventid
    @GetMapping(path = "/student/{studentId}")
    public ResponseEntity<List<EventRegistration>> getEventRegistrationsByUserId(@PathVariable long studentId) {
        return ResponseEntity.ok(eventRegistrationService.getEventRegistrationsByStudentId(studentId));
    }

    @GetMapping(path = "/event/{eventId}")
    public ResponseEntity<List<EventRegistration>> getEventRegistrationsByEventId(@PathVariable long eventId) {
        return ResponseEntity.ok(eventRegistrationService.getEventRegistrationsByEventId(eventId));
    }

    // get specific event registration using studentId and eventid
    @GetMapping(path = "/event/{eventId}/student/{studentId}")
    public ResponseEntity<EventRegistration> getEventRegistrationsByEventIdAndStudentId(
            @PathVariable("eventId") Long eventId, @PathVariable("studentId") Long studentId) {
        return ResponseEntity
                .ok(eventRegistrationService.getEventRegistrationsByEventIdAndStudentId(eventId, studentId));
    }

    // add
    @PostMapping(path = "")
    public ResponseEntity<EventRegistration> addEventRegistration(
            @RequestBody EventRegistrationDTO eventRegistrationDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventRegistrationService.addEventRegistration(eventRegistrationDTO));
    }

    // delete
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        eventRegistrationService.deleteEventRegistration(id);

        return ResponseEntity.ok("Registration deleted");
    }

    // update student registration details
    @PutMapping(path = "/update")
    public ResponseEntity<EventRegistration> updateEventRegistration(
            @RequestBody EventRegistrationDTO eventRegistrationDTO) {
        return ResponseEntity.ok(eventRegistrationService.updateEventRegistration(eventRegistrationDTO));
    }
}
