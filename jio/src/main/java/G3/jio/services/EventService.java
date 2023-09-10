package G3.jio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import G3.jio.entities.Event;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.repositories.EventRepository;

@Service
public class EventService {

    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // list all users
    public List<Event> findAllEvent() {
        return eventRepository.findAll();
    }

    // get user by id
    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).map(event -> {
            return event;
        }).orElse(null);
    }

    // get by name
    public List<Event> getEventByName(String name) {
        return eventRepository.findAllByName(name);
    }

    // save a user
    public Event addEvent(Event newEvent) {
        return eventRepository.save(newEvent);
    }

    // update user
    // not sure what we need to update yet
    public Event updateEvent(Long id, Event newEventInfo) {
        return eventRepository.findById(id).map(event -> {

            return eventRepository.save(event);
        }).orElse(null);
    }

    // delete by id
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException();
        }
        eventRepository.deleteById(id);
    }
}
