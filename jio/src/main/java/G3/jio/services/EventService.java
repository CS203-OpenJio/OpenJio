package G3.jio.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import G3.jio.entities.Event;

@Service
public class EventService implements G3.jio.services.Service {

    private List<Event> events = new ArrayList<>();

    public EventService() {
        events.add(new Event(1L, "Waikiki", LocalDate.of(2023, 1, 1), null, "Beach Day",
                200, "Camp", "Sentosa"));
        events.add(new Event(2L, "DM Workshop", LocalDate.of(2023, 8, 3), null, "SQL, DM",
                40, "Workshop", "SCIS1 SR2-4"));
    }

    public List<Event> listEvents() {
        return events;
    }

    public Event getEvent(Long eventId) {
        for (Event event : events) {
            if (event.getEventId().equals(eventId))
                return event;
        }

        return null;
    }

    public Event updateEvent(Long eventId, Event newEventInfo) {
        for (Event e : events) {
            if (e.getEventId().equals(eventId)) {
                e.setName(newEventInfo.getName());
                e.setStartDate(newEventInfo.getStartDate());
                e.setEndDate(newEventInfo.getEndDate());
                e.setDescription(newEventInfo.getDescription());
                e.setCapacity(newEventInfo.getCapacity());
                e.setEventType(newEventInfo.getEventType());
                e.setVenue(newEventInfo.getVenue());
                return e;
            }
        }
        
        return null;
    }

    public Event addEvent(Event newEvent) {
        events.add(newEvent);
        return newEvent;
    }
}
