package G3.jio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.exceptions.NotExistException;
import G3.jio.repositories.EventRegistrationRepository;

@Service
public class EventRegistrationService {

    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;

    public EventRegistrationService(EventRegistrationRepository eventRegistrationRepository) {
        this.eventRegistrationRepository = eventRegistrationRepository;
    }

    // add
    public EventRegistration addEventRegistration(EventRegistration newEventRegistration) {
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
