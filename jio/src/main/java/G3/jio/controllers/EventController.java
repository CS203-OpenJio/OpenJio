package G3.jio.controllers;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import G3.jio.entities.Student;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequestMapping(path = "api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAllEvent());
    }

    // get event by name using %20 in blankspaces
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<List<Event>> getEventsbyName(@PathVariable String name) {
        name = name.replaceAll("%20", " ");
        List<Event> event = eventService.getEventByName(name);
        if (event == null)
            throw new EventNotFoundException(name);
        return ResponseEntity.ok(eventService.getEventByName(name));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {

        return ResponseEntity.ok(eventService.getEvent(id));
    }

    // // Post event
    // @ResponseStatus(HttpStatus.CREATED)
    // @PostMapping
    // public Event addEvent(@RequestBody EventDTO eventDTO) {
    // return eventService.addEvent(eventDTO);
    // }

    // update event
    @PutMapping(path = "/id/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @Valid @RequestParam("event") String event,  @RequestParam(required = false) MultipartFile imageFile) throws JsonMappingException, JsonProcessingException {

        if (!eventService.existsById(id)) {
            throw new EventNotFoundException();
        }

        ObjectMapper mapper = new ObjectMapper();
        EventDTO eventDTO = mapper.readValue(event, EventDTO.class);

        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO, imageFile));
    }

    // delete event
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EventNotFoundException(id);
        }

        return ResponseEntity.ok("Event deleted");
    }

    // get students signed up for events based on eventId and status
    @PostMapping(path = "/registrations")
    public ResponseEntity<List<Student>> getStudentByEventIdandEventRegistrationStatus(@RequestBody QueryDTO queryDTO) {
        return ResponseEntity.ok(eventService.getStudentByEventIdandEventRegistrationStatus(queryDTO));
    }

    // //uploads image and assigns it to events based on eventId, any previous image
    // is deleted
    // @PostMapping("/upload/{id}")
    // public ResponseEntity<?>
    // uploadImageToFIleSystem(@RequestParam("image")MultipartFile file,
    // @PathVariable Long id) throws IOException {
    // Long uploadImage = storageService.uploadImageToFileSystem(file);
    // Event event = eventService.getEvent(id);
    // if (event.getImage() != null){

    // storageService.deleteImage(event.getImage());
    // }
    // event = eventService.updateEventId(id, uploadImage);

    // // if (event == null)
    // // throw new EventNotFoundException(id);

    // return ResponseEntity.status(HttpStatus.OK)
    // .body(uploadImage);
    // }

    // //downloads image based on eventId
    // @GetMapping("/download/{id}")
    // public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable Long id)
    // throws IOException {
    // Event event = eventService.getEvent(id);
    // Long imageId =event.getImage();
    // byte[] imageData=storageService.downloadImageFromFileSystembyId(imageId);
    // return ResponseEntity.status(HttpStatus.OK)
    // .contentType(MediaType.valueOf("image/png"))
    // .body(imageData);

    // }
}
