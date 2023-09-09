package G3.jio.services;

import java.util.List;

import G3.jio.entities.Events;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.repositories.EventsRepository;

public class EventsService implements Service {

    private EventsRepository eventsRepository;

    public EventsService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    // list all users
    public List<Events> findAllEvents() {
        return eventsRepository.findAll();
    }

    // get user by id
    public Events getEvent(Long eventId) {
        return eventsRepository.findById(eventId).map(event -> {
            return event;
        }).orElse(null);
    }

    // get by name
    public List<Events> getEvenetByName(String name) {
        return eventsRepository.findAllByName();
    }

    // save a user
    public Events addEvent(Events newEvent) {
        return eventsRepository.save(newEvent);
    }

    // update user
    // not sure what we need to update yet
    public Events updateEvent(Long id, Events newEventInfo) {
        return eventsRepository.findById(id).map(event -> {

            return eventsRepository.save(event);
        }).orElse(null);
    }

    // delete by id
    public void deletEvene(Long id) {
        if (!eventsRepository.existsById(id)) {
            throw new EventNotFoundException();
        }
        eventsRepository.deleteById(id);
    }
}
