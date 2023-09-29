package G3.jio.controllers;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

@RestController
@Controller
@RequestMapping(path = "api/v1/register-event")
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    public EventRegistrationController(EventRegistrationService eventRegistrationService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    // get by student or eventid
    @GetMapping(path = "/student/{studentId}")
    public List<EventRegistration> getEventRegistrationsByUserId(@PathVariable long studentId) {
        return eventRegistrationService.getEventRegistrationsByStudentId(studentId);
    }

    @GetMapping(path = "/event/{eventId}")
    public List<EventRegistration> getEventRegistrationsByEventId(@PathVariable long eventId) {
        return eventRegistrationService.getEventRegistrationsByEventId(eventId);
    }

    // get specific event registration using studentId and eventid
    @GetMapping(path = "/event/{eventId}/student/{studentId}")
    public EventRegistration getEventRegistrationsByEventIdAndStudentId(@PathVariable("eventId") Long eventId, @PathVariable("studentId") Long studentId) {

        return eventRegistrationService.getEventRegistrationsByEventIdAndStudentId(eventId, studentId);
    }

    // add
    @PostMapping(path = "")
    public EventRegistration addEventRegistration(@RequestBody EventRegistrationDTO eventRegistrationDTO) {

        return eventRegistrationService.addEventRegistration(eventRegistrationDTO);
    }

    // delete
    @DeleteMapping(path = "/id/{id}")
    public void deleteById(@PathVariable Long id) {
        eventRegistrationService.deleteEventRegistration(id);
    }

    // update student registration details
    @PutMapping(path = "/update")
    public EventRegistration updateEventRegistration(@RequestBody EventRegistrationDTO eventRegistrationDTO) {
        return eventRegistrationService.updateEventRegistration(eventRegistrationDTO);
    }
}
