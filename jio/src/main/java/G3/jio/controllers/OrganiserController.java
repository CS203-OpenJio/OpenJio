package G3.jio.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G3.jio.DTO.EventDTO;
import G3.jio.DTO.QueryDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Organiser;
import G3.jio.services.OrganiserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/organisers")
@RequiredArgsConstructor
@CrossOrigin(origins = "openjio.xyz", allowedHeaders = "*")
public class OrganiserController {

    private final OrganiserService organiserService;

    // get all organisers
    @GetMapping
    public ResponseEntity<List<Organiser>> getAllOrganisers() {
        return ResponseEntity.ok(organiserService.getAllOrganisers());
    }

    // get organiser given their email
    @PostMapping(path = "/email")
    public ResponseEntity<Organiser> getOrganiserByEmail(@RequestBody QueryDTO queryDTO) {
        Organiser organiser = organiserService.getOrganiserByEmail(queryDTO.getEmail());
        if (organiser == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(organiser);
    }

    // post event
    @PostMapping(path = "/create-event")
    public ResponseEntity<Event> postEvent(@Valid @RequestBody EventDTO eventDTO) {

        // System.out.println(eventDTO.getOrganiserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(organiserService.postEvent(eventDTO));
    }

    // delete
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<String> deleteOrganiserById(@PathVariable("id") Long id) {
        organiserService.deleteOrganiser(id);
        return ResponseEntity.ok("Organiser deleted");
    }

    // view events based on organiser email
    @PostMapping(path = "/email/events")
    public ResponseEntity<List<Event>> getEventsByOrganiserEmail(@RequestBody QueryDTO queryDTO) {
        return ResponseEntity.ok(organiserService.getEventsByOrganiserEmail(queryDTO.getEmail()));
    }

    // allocate slots in event
    @PostMapping(path = "/events/allocation")
    public ResponseEntity<List<EventRegistration>> allocateSlotsForEvent(@RequestBody QueryDTO queryDTO) {
        return ResponseEntity.ok(organiserService.allocateSlotsForEvent(queryDTO));
    }

    // set event to 'completed'
    @PostMapping(path = "/events/complete")
    public ResponseEntity<String> completeEvent(@RequestBody QueryDTO queryDTO) {
        organiserService.completeEvent(queryDTO);
        return ResponseEntity.ok("Event has been marked as complete.");
    }
}
