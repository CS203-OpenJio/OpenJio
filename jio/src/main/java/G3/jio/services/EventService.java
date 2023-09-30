package G3.jio.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import G3.jio.DTO.EventDTO;
import G3.jio.entities.Event;
import G3.jio.entities.Organiser;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.exceptions.NotExistException;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.OrganiserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    // list all event
    public List<Event> findAllEvent() {
        return eventRepository.findAll();
    }

    /**
     * Gets Event by id
     * 
     * @param eventId
     * @return
     */
    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).map(event -> {
            return event;
        }).orElse(null);
    }

    /**
     * Gets Event by name
     * 
     * @param name
     * @return
     */
    public List<Event> getEventByName(String name) {
        return eventRepository.findAllByName(name);
    }

    /**
     * Add Event
     * 
     * @param eventDTO
     * @return
     */
    public Event addEvent(EventDTO eventDTO) {

        System.out.println(eventDTO.getName());
        Event event = eventMapToEntity(eventDTO);

        return eventRepository.save(event);
    }

    /**
     * Update existing Event of given id with details from eventDTO
     * 
     * @param id
     * @param eventDTO
     * @return
     */
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

    /**
     * Delete Event of given id
     * 
     * @param id
     */
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException();
        }

        Event event = eventRepository.getReferenceById(id);
        Organiser organiser = event.getOrganiser();
        if (organiser != null) {
            organiser.getEvents().remove(event);
        }

        eventRepository.deleteById(id);
    }

    /**
     * Mapping eventDTO to event entity using ModelMapper
     * 
     * @param eventDTO
     * @return
     */
    private Event eventMapToEntity(EventDTO eventDTO) {
        ModelMapper mapper = new ModelMapper();

        Event event = mapper.map(eventDTO, Event.class);
        return event;
    }
}
