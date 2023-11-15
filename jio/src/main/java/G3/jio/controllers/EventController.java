package G3.jio.controllers;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import G3.jio.DTO.EventDTO;
import G3.jio.DTO.QueryDTO;
import G3.jio.entities.Event;
import G3.jio.entities.Student;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Get all events")
    @GetMapping(path = "/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAllEvent());
    }

    // get event by name using %20 in blankspaces
    @Operation(summary = "Get events by name using %20 in blankspaces")
    @GetMapping(path = "events?name={name}")
    public ResponseEntity<List<Event>> getEventsbyName(@PathVariable String name) {
        name = name.replaceAll("%20", " ");
        List<Event> event = eventService.getEventByName(name);
        if (event == null)
            throw new EventNotFoundException(name);
        return ResponseEntity.ok(eventService.getEventByName(name));
    }

    @Operation(summary = "Get event by id")
    @GetMapping(path = "/events/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable Long eventId) {

        return ResponseEntity.ok(eventService.getEvent(eventId));
    }

    // update event
    @Operation(summary = "Update event, find by id")
    @PutMapping(path = "/events/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId, @Valid @RequestParam("event") String event,  @RequestParam(required = false) MultipartFile imageFile) throws JsonMappingException, JsonProcessingException {

        if (!eventService.existsById(eventId)) {
            throw new EventNotFoundException();
        }

        ObjectMapper mapper = new ObjectMapper();
        EventDTO eventDTO = mapper.readValue(event, EventDTO.class);

        return ResponseEntity.ok(eventService.updateEvent(eventId, eventDTO, imageFile));
    }

    // delete event
    @Operation(summary = "Delete event, find by id")
    @DeleteMapping(path = "/events/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        try {
            eventService.deleteEvent(eventId);
        } catch (EmptyResultDataAccessException e) {
            throw new EventNotFoundException(eventId);
        }

        return ResponseEntity.ok("Event deleted");
    }

    // get students signed up for events based on eventId and status
    @Operation(summary = "Get students signed up for events based on eventId and status")
    @PostMapping(path = "/events/registrations")
    public ResponseEntity<List<Student>> getStudentByEventIdandEventRegistrationStatus(@RequestBody QueryDTO queryDTO) {
        return ResponseEntity.ok(eventService.getStudentByEventIdandEventRegistrationStatus(queryDTO));
    }
}
