package G3.jio.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import G3.jio.DTO.EventDTO;
import G3.jio.DTO.QueryDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Student;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.exceptions.InvalidUserTypeException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.OrganiserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final OrganiserRepository organiserRepository;
    private final StorageServiceAWS storageServiceAWS;

    // list all event
    public List<Event> findAllEvent() {
        return eventRepository.findAll();
    }

    // get event by id
    public Event getEvent(Long eventId) {

        return eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
    }

    // get by name
    public List<Event> getEventByName(String name) {
        List<Event> events = eventRepository.findAllByName(name);

        if (events.isEmpty()) {
            throw new EventNotFoundException();
        }

        return events;
    }

    // update event
    public Event updateEvent(Long eventId, EventDTO eventDTO, MultipartFile imageFile) {

        Event event = getEvent(eventId);

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

        // settle datetime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        if (eventDTO.getStartDateTime() != null) {
            LocalDateTime startDateTime = LocalDateTime.parse(eventDTO.getStartDateTime(), formatter);
            event.setStartDateTime(startDateTime);
        }

        if (eventDTO.getEndDateTime() != null) {
            LocalDateTime endDateTime = LocalDateTime.parse(eventDTO.getEndDateTime(), formatter);
            event.setEndDateTime(endDateTime);
        }

        // settle image
        if (imageFile != null) {
            event.setImage(storageServiceAWS.uploadFile(imageFile));
        }

        eventRepository.saveAndFlush(event);
        return event;
    }

    // delete by id
    public void deleteEvent(Long eventId) {

        Event event = getEvent(eventId);
        

        if (event.getOrganiser() == null) {
            throw new UserNotFoundException("Organiser does not exist!");
        }
        event.getOrganiser().getEvents().remove(event);

        if (event.getImage() != null) {
            
            // Split the string by "/"
            String[] parts = event.getImage().split("/");
            
            // Get the last part (filename) from the array
            String filename = parts[parts.length - 1];
            storageServiceAWS.deleteFile(filename);
        }

        eventRepository.deleteById(eventId);
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

    public boolean existsById(Long id) {
        return eventRepository.existsById(id);
    }
}
