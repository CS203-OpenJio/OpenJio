package G3.jio.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    // get by student or eventid
    @Operation(summary = "Get registration by student Id")
    @GetMapping(path = "/registrations?student={studentId}")
    public ResponseEntity<List<EventRegistration>> getEventRegistrationsByUserId(@PathVariable long studentId) {
        return ResponseEntity.ok(eventRegistrationService.getEventRegistrationsByStudentId(studentId));
    }

    @Operation(summary = "Get registration by event Id")
    @GetMapping(path = "/registrations?event={eventId}")
    public ResponseEntity<List<EventRegistration>> getEventRegistrationsByEventId(@PathVariable long eventId) {
        return ResponseEntity.ok(eventRegistrationService.getEventRegistrationsByEventId(eventId));
    }

    // add
    @Operation(summary = "Add event registration")
    @PostMapping(path = "/registrations")
    public ResponseEntity<EventRegistration> addEventRegistration(
            @RequestBody EventRegistrationDTO eventRegistrationDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(eventRegistrationService.addEventRegistration(eventRegistrationDTO));
    }

    // delete
    @Operation(summary = "Delete registration by Id")
    @DeleteMapping(path = "/registrations/{registrationId}")
    public ResponseEntity<String> deleteById(@PathVariable Long registrationId) {
        eventRegistrationService.deleteEventRegistration(registrationId);

        return ResponseEntity.ok("Registration deleted");
    }

    // update student registration details
    @Operation(summary = "Update student registration details")
    @PutMapping(path = "/registrations/update")
    public ResponseEntity<EventRegistration> updateEventRegistration(
            @RequestBody EventRegistrationDTO eventRegistrationDTO) {
        return ResponseEntity.ok(eventRegistrationService.updateEventRegistration(eventRegistrationDTO));
    }
}
