package G3.jio.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import G3.jio.DTO.EventDTO;
import G3.jio.DTO.QueryDTO;
import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Organiser;
import G3.jio.services.OrganiserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrganiserController {

    private final OrganiserService organiserService;

    // get all organisers
    @Operation(summary = "Get all organisers")
    @GetMapping(path = "/organisers")
    public ResponseEntity<List<Organiser>> getAllOrganisers() {
        return ResponseEntity.ok(organiserService.getAllOrganisers());
    }

    // get organiser given their email
    @Operation(summary = "Get organiser by email")
    @PostMapping(path = "organisers/email")
    public ResponseEntity<Organiser> getOrganiserByEmail(@RequestBody QueryDTO queryDTO) {
        Organiser organiser = organiserService.getOrganiserByEmail(queryDTO.getEmail());
        if (organiser == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(organiser);
    }

    // post event
    @Operation(summary = "Create event")
    @PostMapping(path = "/organisers/events")
    public ResponseEntity<Event> postEvent(@RequestParam("event") String event,  @RequestParam(required = false) MultipartFile imageFile) throws JsonMappingException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        EventDTO eventDTO = mapper.readValue(event, EventDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(organiserService.postEvent(eventDTO, imageFile));
    }

    // delete
    @Operation(summary = "Delete organiser by organiserId")
    @DeleteMapping(path = "/organisers/{organiserId}")
    public ResponseEntity<String> deleteOrganiserById(@PathVariable("id") Long id) {
        organiserService.deleteOrganiser(id);
        return ResponseEntity.ok("Organiser deleted");
    }

    // view events based on organiser email
    @Operation(summary = "View events based on organiser email")
    @PostMapping(path = "/organisers/email/events")
    public ResponseEntity<List<Event>> getEventsByOrganiserEmail(@RequestBody QueryDTO queryDTO) {
        return ResponseEntity.ok(organiserService.getEventsByOrganiserEmail(queryDTO.getEmail()));
    }

    // allocate slots in event
    @Operation(summary = "Allocates slots in a event, find event by eventId")
    @PutMapping(path = "/organisers/events/allocation")
    public ResponseEntity<List<EventRegistration>> allocateSlotsForEvent(@RequestBody QueryDTO queryDTO) {
        return ResponseEntity.ok(organiserService.allocateSlotsForEvent(queryDTO));
    }

    // set event to 'completed'
    @Operation(summary = "Set event to complete, find event by eventId")
    @PutMapping(path = "/organisers/events/completion")
    public ResponseEntity<String> completeEvent(@RequestBody QueryDTO queryDTO) {
        organiserService.completeEvent(queryDTO);
        return ResponseEntity.ok("Event has been marked as complete.");
    }
}
