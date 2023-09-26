package G3.jio.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.DTO.EventRegistrationDTO;
import G3.jio.entities.EventRegistration;
import G3.jio.services.EventRegistrationService;
import G3.jio.entities.Event;

@RestController
@Controller
@RequestMapping(path = "api/v1/register")
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    public EventRegistrationController(EventRegistrationService eventRegistrationService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    // get by userid or eventid
    @GetMapping(path = "/student/{studentId}")
    public List<Event> getEventRegistrationsByUserId(@PathVariable long studentId) {
        return eventRegistrationService.getEventRegistrationsByStudentId(studentId);
    }
    // get array of events based on studentId

    @GetMapping(path = "/event/{eventId}")
    public List<EventRegistration> getEventRegistrationsByEventId(@PathVariable long eventId) {
        return eventRegistrationService.getEventRegistrationsByEventId(eventId);
    }
    // get array of students based on eventId

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
}
