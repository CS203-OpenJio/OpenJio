package G3.jio.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import G3.jio.DTO.EventDTO;
import G3.jio.DTO.OrganiserDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Organiser;
import G3.jio.entities.Student;
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

    /**
     * Get Organiser with id
     * 
     * @param organiserId
     * @return
     */
    public Organiser getOrganiser(Long organiserId) {
        return organiserRepository.findById(organiserId).map(organiser -> {
            return organiser;
        }).orElse(null);
    }

    /**
     * Get all Organiser
     * 
     * @return
     */
    public List<Organiser> getAllOrganisers() {
        return organiserRepository.findAll();
    }

    /**
     * Update organiser of given id with details from organiserDTO
     * 
     * @param id
     * @param organiserDTO
     * @return
     */
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

    /**
     * Delete organiser of given id
     * 
     * @param id
     */
    public void deleteOrganiser(Long id) {
        if (!organiserRepository.existsById(id)) {
            throw new NotExistException("Organiser");
        }
        organiserRepository.deleteById(id);
    }

    /**
     * Create new event by organiser with details from eventDTO
     * 
     * @param eventDTO
     * @return
     */
    public Event postEvent(EventDTO eventDTO) {

        // find organiser
        if (!organiserRepository.existsById(eventDTO.getOrganiserId())) {
            throw new NotExistException("Organiser");
        }
        Organiser organiser = organiserRepository.getReferenceById(eventDTO.getOrganiserId());

        Event event = eventMapToEntity(eventDTO);
        event.setOrganiser(organiser);
        organiser.getEvents().add(event);

        return eventRepository.save(event);
    }

    /**
     * Update existing Event of given id with details from eventDTO
     * 
     * @param id
     * @param eventDTO
     * @return
     */
    private Event eventMapToEntity(EventDTO eventDTO) {
        ModelMapper mapper = new ModelMapper();

        Event event = mapper.map(eventDTO, Event.class);
        return event;
    }

    /**
     * Get organiser by email
     * 
     * @param email
     * @return
     */
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

    /**
     * Get all events by organiser with email
     * 
     * @param email
     * @return
     */
    public List<Event> getEventsByOrganiserEmail(String email) {

        Organiser organiser = getOrganiserByEmail(email);
        return eventRepository.findAllByOrganiserId(organiser.getId());
    }
}
