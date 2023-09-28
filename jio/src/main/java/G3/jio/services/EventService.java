package G3.jio.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import G3.jio.DTO.EventDTO;
import G3.jio.entities.Event;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.repositories.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // list all event
    public List<Event> findAllEvent() {
        return eventRepository.findAll();
    }

    // get event by id
    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).map(event -> {
            return event;
        }).orElse(null);
    }

    // get by name
    public List<Event> getEventByName(String name) {
        return eventRepository.findAllByName(name);
    }

    // save a event
    public Event addEvent(EventDTO eventDTO) {

        Event event = eventMapToEntity(eventDTO);
        
        return eventRepository.save(event);
    }

    // update event
    // not sure what we need to update yet
    public Event updateEvent(Long id, EventDTO eventDTO) {
        Optional<Event> o = eventRepository.findById(id);
        if (!o.isPresent()) {
            throw new EventNotFoundException();
        }
        Event event = o.get();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(eventDTO, event);
        eventRepository.saveAndFlush(event);

        return event;
    }

    // delete by id
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException();
        }
        eventRepository.deleteById(id);
    }

    private Event eventMapToEntity(EventDTO eventDTO) {
        ModelMapper mapper = new ModelMapper();

        Event event = mapper.map(eventDTO, Event.class);
        return event;
    }
}
