package G3.jio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import G3.jio.DTO.EventRegistrationDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.User;
import G3.jio.exceptions.NotExistException;
import G3.jio.repositories.EventRegistrationRepository;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.UserRepository;

@Service
public class EventRegistrationService {

    @Autowired
    private final EventRegistrationRepository eventRegistrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public EventRegistrationService(EventRegistrationRepository eventRegistrationRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    // add
    public EventRegistration addEventRegistration(EventRegistrationDTO newEventRegistrationDTO) {
        User user = userRepository.getReferenceById(newEventRegistrationDTO.getUserId());
        Event event = eventRepository.getReferenceById(newEventRegistrationDTO.getEventId());
        EventRegistration newEventRegistration = new EventRegistration(user, event, newEventRegistrationDTO.isDeregistered(), newEventRegistrationDTO.isSuccessful());
        return eventRegistrationRepository.save(newEventRegistration);
    }

    // get registrations by userId
    public List<EventRegistration> getEventRegistrationsByUserId(long userId) {
        return eventRegistrationRepository.findAllByUserId(userId);
    }

    // get registrations by eventId
    public List<EventRegistration> getEventRegistrationsByEventId(long eventId) {
        return eventRegistrationRepository.findAllByEventId(eventId);
    }

    //delete
    public void deleteEventRegistration(Long id) {
        if (!eventRegistrationRepository.existsById(id)) {
            throw new NotExistException("Registration");
        }
        eventRegistrationRepository.deleteById(id);
    }
}
