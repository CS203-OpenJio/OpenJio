package G3.jio.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import G3.jio.DTO.EventDTO;
import G3.jio.DTO.QueryDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.repositories.EventRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

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

        System.out.println(eventDTO.getName());
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

        Event event = eventRepository.getReferenceById(id);
        Organiser organiser = event.getOrganiser();
        if (organiser != null) {
            organiser.getEvents().remove(event);
        }
        
        eventRepository.deleteById(id);
    }

    private Event eventMapToEntity(EventDTO eventDTO) {
        ModelMapper mapper = new ModelMapper();

        Event event = mapper.map(eventDTO, Event.class);
        
        // settle datetime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime startDateTime = LocalDateTime.parse(eventDTO.getStartDateTime(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(eventDTO.getEndDateTime(), formatter);
        event.setStartDateTime(startDateTime);
        event.setEndDateTime(endDateTime);

        return event;
    }

    public List<Student> getStudentByEventIdandEventRegistrationStatus(QueryDTO queryDTO) {

        Event event = getEvent(queryDTO.getEventId());
        List<EventRegistration> registrations = event.getRegistrations();
        List<Student> students = new ArrayList<>();

        for (EventRegistration registration : registrations) {

            if (registration.getStatus() == queryDTO.getStatus() || queryDTO.getStatus() == null) {
                students.add(registration.getStudent());
            }
        }

        return students;
    }
}
