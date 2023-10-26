package G3.jio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import G3.jio.DTO.EventDTO;
import G3.jio.DTO.QueryDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.exceptions.InvalidUserTypeException;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.OrganiserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final OrganiserRepository organiserRepository;

    // list all event
    public List<Event> findAllEvent() {
        return eventRepository.findAll();
    }

    // get event by id
    public Event getEvent(Long eventId) {
        Optional<Event> o = eventRepository.findById(eventId);
        if (!o.isPresent()) {
            throw new EventNotFoundException("Event does not exist!");
        }
        Event event = o.get();
        return event;
    }

    // get by name
    public List<Event> getEventByName(String name) {
        List<Event> events = eventRepository.findAllByName(name);

        if (events.isEmpty()) {
            throw new EventNotFoundException("Event does not exist!");
        }

        return events;
    }

    // // save a event
    // public Event addEvent(EventDTO eventDTO) {

    // System.out.println(eventDTO.getName());
    // Event event = eventMapToEntity(eventDTO);
    // return eventRepository.save(event);
    // }

    // private Event eventMapToEntity(EventDTO eventDTO) {
    // ModelMapper mapper = new ModelMapper();

    // Event event = mapper.map(eventDTO, Event.class);

    // // settle datetime
    // DateTimeFormatter formatter =
    // DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    // LocalDateTime startDateTime =
    // LocalDateTime.parse(eventDTO.getStartDateTime(), formatter);
    // LocalDateTime endDateTime = LocalDateTime.parse(eventDTO.getEndDateTime(),
    // formatter);
    // event.setStartDateTime(startDateTime);
    // event.setEndDateTime(endDateTime);

    // return event;
    // }

    // update event
    public Event updateEvent(Long id, EventDTO eventDTO) {

        Event event = getEvent(id);

        // read from jwt token
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        if (!organiserRepository.existsByEmail(email)) {
            throw new InvalidUserTypeException("Account is not an organiser!");
        }

        // get organiser id from event
        // check if org id is same
        if (!organiserRepository.findByEmail(email).get().equals(event.getOrganiser())) {
            throw new InvalidUserTypeException("Account is not creator of this event!");
        }
        
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
            .setSkipNullEnabled(true)
            .setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.map(eventDTO, event);
        eventRepository.saveAndFlush(event);
        
        return event;
    }

    


    // delete by id
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException("Event does not exist!");
        }

        Event event = eventRepository.getReferenceById(id);
        Organiser organiser = event.getOrganiser();
        if (organiser != null) {
            organiser.getEvents().remove(event);
        }

        eventRepository.deleteById(id);
    }

    // private Event eventMapToEntity(EventDTO eventDTO) {
    // ModelMapper mapper = new ModelMapper();

    // Event event = mapper.map(eventDTO, Event.class);

    // // settle datetime
    // DateTimeFormatter formatter =
    // DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    // LocalDateTime startDateTime =
    // LocalDateTime.parse(eventDTO.getStartDateTime(), formatter);
    // LocalDateTime endDateTime = LocalDateTime.parse(eventDTO.getEndDateTime(),
    // formatter);
    // event.setStartDateTime(startDateTime);
    // event.setEndDateTime(endDateTime);

    // return event;
    // }

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
