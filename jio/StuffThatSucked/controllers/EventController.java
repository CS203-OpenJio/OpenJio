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

import G3.jio.entities.Event;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.services.EventService;

@Controller
@RestController
public class EventController {

    public final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/api/v1/events")
    public List<Event> listEvents() {
        return eventService.listEvents();
    }

    /**
     * Search for event with the given id
     * If there is no event with the given "id", throw a EventNotFoundException
     * 
     * @param id
     * @return event with the given id
     */
    @GetMapping("/api/v1/events/{id}")
    public Event getEvent(@PathVariable Long id) {
        Event event = eventService.getEvent(id);

        // Need to handle "event not found" error using proper HTTP status code
        // In this case it should be HTTP 404
        if (event == null) {
            throw new EventNotFoundException(id);
        }

        // return null;
        return eventService.getEvent(id);
    }

    /**
     * Add a new event with POST request to "/events"
     * Note the use of @RequestBody
     * 
     * @param event
     * @return list of all events
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/events")
    public Event addEvent(@RequestBody Event event) {
        return eventService.addEvent(event);
    }

    /**
     * If there is no event with the given "id", throw a EventNotFoundException
     * 
     * @param id
     * @param newEventInfo
     * @return the updated, or newly added event
     */
    @PutMapping("/api/v1/events/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event newEventInfo) {

        Event event = eventService.updateEvent(id, newEventInfo);
        if (event == null) {
            throw new EventNotFoundException(id);
        }

        return event;
    }
}
