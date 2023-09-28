package G3.jio.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import G3.jio.DTO.EventDTO;
import G3.jio.entities.Event;
import G3.jio.entities.Organiser;
import G3.jio.services.OrganiserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/organisers")
@RequiredArgsConstructor
public class OrganiserController {
    
    private final OrganiserService organiserService;

    @GetMapping
    public List<Organiser> getAllOrganisers() {
        return organiserService.getAllOrganisers();
    }

    @PostMapping(path = "/create-event")
    public Event posteeEvent(@RequestBody EventDTO eventDTO) {

        System.out.println(eventDTO.getOrganiserId());
        return organiserService.postEvent(eventDTO);
    }

    @DeleteMapping(path = "/id/{id}")
    public void deleteOrganiserById(@PathVariable("id") Long id) {
        organiserService.deleteOrganiser(id);
    }
}
