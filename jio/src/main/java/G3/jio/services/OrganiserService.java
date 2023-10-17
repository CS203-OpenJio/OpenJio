package G3.jio.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import G3.jio.DTO.AllocationDTO;
import G3.jio.DTO.EventDTO;
import G3.jio.DTO.OrganiserDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Organiser;
import G3.jio.entities.Status;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.exceptions.NotExistException;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.EventRegistrationRepository;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.OrganiserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganiserService {

    private final OrganiserRepository organiserRepository;
    private final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;

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
            throw new UserNotFoundException("Organiser does not exist!");
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
            throw new UserNotFoundException("Organiser does not exist!");
        }
        organiserRepository.deleteById(id);
    }

    // organiser post event
    public Event postEvent(EventDTO eventDTO) {

        Organiser organiser = null;
        Long organiserId = eventDTO.getOrganiserId();
        if (organiserId == null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String email = userDetails.getUsername();
            organiser = this.getOrganiserByEmail(email);

        } else if (!organiserRepository.existsById(organiserId)) {
            throw new UserNotFoundException("Organiser does not exist!");

        } else {
            organiser = organiserRepository.getReferenceById(eventDTO.getOrganiserId());
        }

        Event event = eventMapToEntity(eventDTO);
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
    public List<EventRegistration> allocateSlotsForEvent(AllocationDTO allocationDTO) {

        Long eventId = allocationDTO.getEventId();
        String algo = allocationDTO.getAlgo();

        if (algo.equals("FCFS")) {
            return allocateSlotsForEventFCFS(eventId);

        } else if (algo.equals("Random")) {
            return allocateSlotsForEventRandom(eventId);
        }

        return null;
    }

    private List<EventRegistration> allocateSlotsForEventRandom(Long eventId) {

        // get event
        Event event = getEvent(eventId);

        // get applications
        event.setAlgo("Random");
        List<EventRegistration> result = new ArrayList<>();
        List<EventRegistration> applications = event.getRegistrations();

        // initialise stuff
        Set<Integer> randomInt = new HashSet<>();
        Random rand = new Random();
        int capacity = event.getCapacity();

        // if applicants < capacity, accept all
        if (applications.size() < capacity) {
            for (EventRegistration er : applications) {
                er.setStatus(Status.ACCEPTED);
                result.add(er);
                eventRegistrationRepository.saveAndFlush(er);
            }

            return result;
        }

        // get random number of random indexes
        while (randomInt.size() != capacity) {
            randomInt.add(rand.nextInt(applications.size()));
        }

        // set statuses to accept the selected index and reject the rest
        for (int i = 0; i < applications.size(); i++) {

            EventRegistration registration = applications.get(i);

            if (randomInt.contains(i)) {
                registration.setStatus(Status.ACCEPTED);
                result.add(registration);

            } else {
                registration.setStatus(Status.REJECTED);
            }

            eventRegistrationRepository.saveAndFlush(registration);
        }

        return result;
    }

    // FCFS
    private List<EventRegistration> allocateSlotsForEventFCFS(Long eventId) {

        Event event = getEvent(eventId);

        event.setAlgo("FCFS");
        List<EventRegistration> result = new ArrayList<>();
        List<EventRegistration> applications = event.getRegistrations();
        applications.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));

        for (int i = 0; i < applications.size(); i++) {

            EventRegistration registration = applications.get(i);

            if (i < event.getCapacity()) {
                registration.setStatus(Status.ACCEPTED);
                result.add(registration);

            } else {
                registration.setStatus(Status.REJECTED);
            }

            eventRegistrationRepository.saveAndFlush(registration);
        }

        return result;
    }

    public Event getEvent(Long eventId) {
        Optional<Event> o = eventRepository.findById(eventId);
        if (!o.isPresent()) {
            throw new EventNotFoundException();
        }
        Event event = o.get();
        return event;
    }
}
