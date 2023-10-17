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
import org.springframework.web.bind.annotation.RestController;

import G3.jio.DTO.EventDTO;
import G3.jio.DTO.QueryDTO;
import G3.jio.entities.Event;
import G3.jio.entities.Student;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.services.EventService;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequestMapping(path = "api/v1/events")
@RequiredArgsConstructor
public class EventController {

    final private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAllEvent());
    }

    // get event by name using %20 in blankspaces
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<List<Event>> getEventsbyName(@PathVariable String name) {
        name = name.replaceAll("%20", " ");
        List<Event> event = eventService.getEventByName(name);
        if (event == null)
            throw new EventNotFoundException(name);
        return ResponseEntity.ok(eventService.getEventByName(name));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
   
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    // // Post event
    // @ResponseStatus(HttpStatus.CREATED)
    // @PostMapping
    // public Event addEvent(@RequestBody EventDTO eventDTO) {
    //     return eventService.addEvent(eventDTO);
    // }

    // update event
    @PutMapping(path = "/id/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        Event event = eventService.updateEvent(id, eventDTO);
        if (event == null)
            throw new EventNotFoundException(id);

        return ResponseEntity.ok(event);
    }

    // delete event
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EventNotFoundException(id);
        }

        return ResponseEntity.ok("Event deleted");
    }

    // get students signed up for events based on eventId and status
    @PostMapping(path = "/registrations")
    public ResponseEntity<List<Student>> getStudentByEventIdandEventRegistrationStatus(@RequestBody QueryDTO queryDTO) {
        return ResponseEntity.ok(eventService.getStudentByEventIdandEventRegistrationStatus(queryDTO));
    }
}
