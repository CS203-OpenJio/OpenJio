package G3.jio.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import G3.jio.DTO.EventDTO;
import G3.jio.DTO.OrganiserDTO;
import G3.jio.entities.Event;
import G3.jio.entities.Organiser;
import G3.jio.exceptions.NotExistException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.OrganiserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganiserService {
    
    private final OrganiserRepository organiserRepository;
    private final EventRepository eventRepository;

    // get
    public Organiser getOrganiser(Long organiserId) {
        return organiserRepository.findById(organiserId).map(organiser -> {
            return organiser;
        }).orElse(null);
    }

    // list all Organiser
    public List<Organiser> getAllOrganisers() {
        return organiserRepository.findAll();
    }

    // update organiser
    public Organiser updateOrganiser(Long id, OrganiserDTO organiserDTO) {
        Optional<Organiser> o = organiserRepository.findById(id);
        if (!o.isPresent()) {
            throw new UserNotFoundException();
        }
        Organiser organiser = o.get();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(organiserDTO, organiser);
        organiserRepository.saveAndFlush(organiser);

        return organiser;
    }

    // delete by id
    public void deleteOrganiser(Long id) {
        if (!organiserRepository.existsById(id)) {
            throw new NotExistException("Organiser");
        }
        organiserRepository.deleteById(id);
    }

    // organiser post event
    public Event postEvent(EventDTO eventDTO) {

        Organiser organiser = null;
        Long organiserId = eventDTO.getOrganiserId();
        if (organiserId == null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = userDetails.getUsername();
            organiser = this.getOrganiserByEmail(email);

        } else if (!organiserRepository.existsById(organiserId)) {
            throw new NotExistException("Organiser");

        } else {
            organiser = organiserRepository.getReferenceById(eventDTO.getOrganiserId());
        }

        Event event = new Event();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(eventDTO, event);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime startDateTime = LocalDateTime.parse(eventDTO.getStartDateTime(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(eventDTO.getEndDateTime(), formatter);
        event.setStartDateTime(startDateTime);
        event.setEndDateTime(endDateTime);

        event.setOrganiser(organiser);
        organiser.getEvents().add(event);

        return eventRepository.save(event);
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

    public Organiser getOrganiserByEmail(String email) {
        Organiser organiser = organiserRepository.findByEmail(email).map(o -> {
            return o;
        }).orElse(null);

        if (organiser == null) {
            throw new NotExistException("organsiser");
        } else {
            return organiser;
        }
    }

    public List<Event> getEventsByOrganiserEmail(String email) {

        Organiser organiser = getOrganiserByEmail(email);
        return eventRepository.findAllByOrganiserId(organiser.getId());
    }
}
