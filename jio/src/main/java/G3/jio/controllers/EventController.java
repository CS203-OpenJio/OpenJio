package G3.jio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.entities.Event;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.services.EventService;

@RestController
@Controller
@RequestMapping(path = "api/v1/events")
public class EventController {
    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.findAllEvent();
    }

    @GetMapping(path = "/{id}")
    public Event getEvent(@PathVariable Long id) {
        Event event = eventService.getEvent(id);

        // Need to handle "Event not found" error using proper HTTP status code
        // In this case it should be HTTP 404
        if (event == null)
            throw new EventNotFoundException(id);
        return eventService.getEvent(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Event addEvent(@RequestBody Event event) {
        return eventService.addEvent(event);
    }

    @PutMapping(path = "{id}")
    public Event updateevent(@PathVariable Long id, @RequestBody Event newEventInfo) {
        Event event = eventService.updateEvent(id, newEventInfo);
        if (event == null)
            throw new EventNotFoundException(id);
        return event;
    }

    @DeleteMapping(path = "{id}")
    public void deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EventNotFoundException(id);
        }
    }

}
