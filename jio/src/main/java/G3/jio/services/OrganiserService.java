package G3.jio.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import G3.jio.DTO.EventDTO;
import G3.jio.DTO.OrganiserDTO;
import G3.jio.DTO.QueryDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Organiser;
import G3.jio.exceptions.CustomErrorException;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.exceptions.InvalidUserTypeException;
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
    private final AlgoService algoService;
    private final StorageServiceAWS storageServiceAWS;

    // get
    public Organiser getOrganiser(Long organiserId) {

        return organiserRepository.findById(organiserId).orElseThrow(() -> new UserNotFoundException("Organiser does not exist!"));
    }

    // list all Organiser
    public List<Organiser> getAllOrganisers() {
        return organiserRepository.findAll();
    }

    // update organiser
    public Organiser updateOrganiser(Long id, OrganiserDTO organiserDTO) {

        Organiser organiser = getOrganiser(id);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(organiserDTO, organiser);
        organiserRepository.saveAndFlush(organiser);

        return organiser;
    }

    // delete by id
    public void deleteOrganiser(Long id) {
        if (!organiserRepository.existsById(id)) {
            throw new UserNotFoundException("Organiser does not exist!");
        }
        organiserRepository.deleteById(id);
    }

    // organiser post event
    public Event postEvent(EventDTO eventDTO, MultipartFile imageFile) {

        Organiser organiser = getOrganiserFromTokenOrDTO(eventDTO);

        Event event = eventMapToEntity(eventDTO);
        event.setOrganiser(organiser);

        if (imageFile != null) {
            event.setImage(storageServiceAWS.uploadFile(imageFile));
        }
        organiser.getEvents().add(event);

        return eventRepository.save(event);
    }

    // retrieves organiser from DTO or from token
    private Organiser getOrganiserFromTokenOrDTO(EventDTO eventDTO) {

        Long organiserId = eventDTO.getOrganiserId();

        // if no orgId provided in DTO, read from token
        if (organiserId == null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String email = userDetails.getUsername();
            return this.getOrganiserByEmail(email);

        // else if orgId is provided, check that it exists
        } else {
            return organiserRepository.findById(organiserId).orElseThrow(() -> new UserNotFoundException("Organiser does not exist!"));
        }
    }

    private Event eventMapToEntity(EventDTO eventDTO) {
        ModelMapper mapper = new ModelMapper();

        // make sure org id doesnt get mapped to eventId, turning post method to PUT
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
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
            throw new UserNotFoundException("Organiser does not exist!");
        } else {
            return organiser;
        }
    }

    public List<Event> getEventsByOrganiserEmail(String email) {

        Organiser organiser = getOrganiserByEmail(email);
        return eventRepository.findAllByOrganiserId(organiser.getId());
    }

    // redirects based on algo type
    public List<EventRegistration> allocateSlotsForEvent(QueryDTO queryDTO) {

        // read from jwt token
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        if (!organiserRepository.existsByEmail(email)) {
            throw new InvalidUserTypeException("Account is not an organiser!");
        }

        // get organiser id
        Event event = getEvent(queryDTO.getEventId());

        // check if org id is same
        if (!getOrganiserByEmail(email).equals(event.getOrganiser())) {
            throw new InvalidUserTypeException("Account is not creator of this event!");
        }

        // check if event is completed
        if (event.isCompleted()) {
            throw new CustomErrorException("Event is already completed!");
        }

        String algo;
        if (queryDTO.getAlgo() != null) {
            algo = queryDTO.getAlgo();

        } else if (event.getAlgo() != null) {
            algo = event.getAlgo();

        } else {
            throw new NotExistException("The event has no allocation type!");
        }

        if (algo.equals("FCFS")) {
            return algoService.allocateSlotsForEventFCFS(event);

        } else if (algo.equals("Random")) {
            return algoService.allocateSlotsForEventRandom(event);

        } else if (algo.equals("Weighted Random")) {
            return algoService.allocateSlotsForEventWeightedRandom(event);

        } else if (algo.equals("Score")) {
            return algoService.allocateSlotsForEventScore(event);
        }

        throw new CustomErrorException("The event does not have a valid allocation type!");
    }

    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
    }

    public void completeEvent(QueryDTO queryDTO) {

        Event event = getEvent(queryDTO.getEventId()); 

        // read from jwt token
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        if (!organiserRepository.existsByEmail(email)) {
            throw new InvalidUserTypeException("Account is not an organiser!");
        }

        // check if org id is same
        if (!getOrganiserByEmail(email).equals(event.getOrganiser())) {
            throw new InvalidUserTypeException("Account is not creator of this event!");
        }
        
        event.setCompleted(true);
        event.setVisible(false);
        List<EventRegistration> registrations = event.getRegistrations();
        Iterator<EventRegistration> it = registrations.iterator();
        while (it.hasNext()) {

            EventRegistration er = it.next();

            er.setCompleted(true);
            er.setTimeCompleted(LocalDateTime.now());
        }
        eventRepository.saveAndFlush(event);
    }
}
