
package G3.jio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import G3.jio.entities.Event;
import G3.jio.entities.Organiser;
import G3.jio.exceptions.UserNotFoundException;
import G3.jio.repositories.EventRepository;
import G3.jio.repositories.OrganiserRepository;
import G3.jio.repositories.StudentRepository;

class OrganiserServiceTest {

    @Mock
    private OrganiserRepository organiserRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private OrganiserService organiserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll_AllOrganisers_ReturnAllOrganisers(){

        // arrange
        Organiser organiser1 = new Organiser();
        organiser1.setEmail("organiser1@test.com");
        Organiser organiser2 = new Organiser();
        organiser2.setEmail("organiser2@test.com");
        Organiser organiser3 = new Organiser();
        organiser3.setEmail("organiser3@test.com");
        Organiser organiser4 = new Organiser();
        organiser4.setEmail("organiser4@test.com");
        List<Organiser> organiserOrganiserList = List.of(organiser1, organiser2, organiser3, organiser4);

        when(organiserRepository.findAll()).thenReturn(organiserOrganiserList);

        // act 
        List<Organiser> responseList = organiserService.getAllOrganisers();
        
        //assert
        verify(organiserRepository, times(1)).findAll();
        assertEquals(organiserOrganiserList, responseList);
        
    }

    @Test
    void testGetAll_NoOrganisers_ReturnEmptyList(){

        // arrange
        when(organiserRepository.findAll()).thenReturn(new ArrayList<Organiser>());

        // act 
        List<Organiser> responseList = organiserService.getAllOrganisers();
        
        //assert
        verify(organiserRepository, times(1)).findAll();
        assertEquals(responseList, new ArrayList<Organiser>());
        
    }
    
    @Test
    void testGetOrganiserByEmail_Exist_ReturnOrganiser(){

        // arrange
        Organiser organiserOrganiser = new Organiser();
        organiserOrganiser.setEmail("john.doe@example.com");;

        Optional<Organiser> optionalOrganiser = Optional.of(organiserOrganiser);
        when(organiserRepository.findByEmail(any(String.class))).thenReturn(optionalOrganiser);

        // act 
        Organiser responseOrganiser = organiserService.getOrganiserByEmail(organiserOrganiser.getEmail());

        //assert
        assertEquals(responseOrganiser, organiserOrganiser);
        verify(organiserRepository, times(1)).findByEmail(organiserOrganiser.getEmail());

    }

    @Test
    void testGetOrganiserByEmail_NotFound_ThrowUserNotFound(){

        // arrange
        Organiser organiserOrganiser = new Organiser();
        organiserOrganiser.setEmail("john.doe@example.com");
        String organiserOrganiserEmailToSearch = "mary.jane@example.com";
        
        when(organiserRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        // act and assert
        Exception exception = assertThrows(UserNotFoundException.class, () -> organiserService.getOrganiserByEmail(organiserOrganiserEmailToSearch));

        //assert
        verify(organiserRepository, times(1)).findByEmail(organiserOrganiserEmailToSearch);
        assertEquals(exception.getMessage(), "User Not Found: Email not found");
        
    }



    @Test
    void testDeleteOrganiser_OrganiserExists_ReturnSuccess() {

        // Arrange
        String name = "Daniel";
        String email = "test@test.com";
        Long id = 1L;
        Organiser originalOrganiser = new Organiser();
        originalOrganiser.setId(1L);
        originalOrganiser.setName(name);
        originalOrganiser.setEmail(email);

        when(organiserRepository.existsById(any(Long.class))).thenReturn(true);

        // Act
        organiserService.deleteOrganiser(originalOrganiser.getId());

        // Assert
        verify(organiserRepository, times(1)).existsById(id);
        verify(organiserRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteOrganiser_OrganiserNotExist_ThrowsUserNotFoundException() {

        // Arrange
        String exceptionMsg = "";
        String name = "Daniel";
        String email = "test@test.com";
        Long id = 1L;
        Organiser originalOrganiser = new Organiser();
        originalOrganiser.setId(1L);
        originalOrganiser.setName(name);
        originalOrganiser.setEmail(email);

        when(organiserRepository.existsById(any(Long.class))).thenReturn(false);

        // Act
        try {
            organiserService.deleteOrganiser(id);
        } catch (UserNotFoundException e) {
            exceptionMsg = e.getMessage();
        }

        // Assert
        assertEquals("User Not Found: Organiser does not exist!", exceptionMsg);
        verify(organiserRepository, times(1)).existsById(id);
    }

    @Test
    void testGetAllEventsByOrganiser_OrganiserNotExist_ThrowsUserNotFoundException() {
        // Arrange
        Event event1 = new Event();
        event1.setId(1L);
        event1.setName("event1");
        Event event2 = new Event();
        event2.setId(2L);
        event2.setName("event2");
        Event event3 = new Event();
        event3.setId(3L);
        event3.setName("event3");
        List<Event> eventList = List.of(event1, event2, event3);

        Organiser organiser = new Organiser();
        organiser.setId(1L);
        organiser.setEvents(eventList);
        organiser.setEmail("test@test.com");

        when(eventRepository.findAllByOrganiserId(any(Long.class))).thenReturn(eventList);
        when(organiserRepository.findByEmail(any(String.class))).thenReturn(Optional.of(organiser));

        // Act
        List<Event> responseList = organiserService.getEventsByOrganiserEmail(organiser.getEmail());

        // Assert
        assertEquals(eventList, responseList);
        verify(eventRepository, times(1)).findAllByOrganiserId(any(Long.class));
    }

    /********************* (WIP) We currently do not allow changes to email and name *********************/

    // @Test
    // void testUpdateOrganiser_OrganiserExists_ReturnOrganiser() {

    //     // Arrange
    //     //UpdateOrganiserDetailsDTO updateOrganiserDetailsDTO = new UpdateOrganiserDetailsDTO("Jacky", "jacky@yahoo.com.sg", null, 1000);
    //     Organiser newOrganiser = new Organiser();
    //     newOrganiser.setName("Daniel");
    //     newOrganiser.setEmail("newemail@test.com");
    //     Organiser originalOrganiser = new Organiser();
    //     originalOrganiser.setId(1L);
    //     originalOrganiser.setName(newOrganiser.getEmail());
    //     originalOrganiser.setEmail(newOrganiser.getEmail());

    //     when(organiserRepository.existsByEmail(any(String.class)))
    //         .thenReturn(false);
    //     when(organiserRepository.getById(any(Long.class)))
    //         .thenReturn(Optional.of(originalOrganiser));
    //     when(organiserRepository.getByEmail(anyString()))
    //         .thenReturn(Optional.of(newOrganiser));
    //     when(organiserRepository.saveAndFlush(any(Organiser.class)))
    //         .thenReturn(newOrganiser);

    //     // Act
    //     Organiser responseOrganiser = organiserService.updateOrganiser(originalOrganiser.getId(), newOrganiser);

    //     // Assert
    //     assertEquals(originalOrganiser, responseOrganiser);
    //     verify(organiserRepository, times(1)).existsByEmail(newOrganiser.getEmail());
    //     verify(organiserRepository, times(1)).getByEmail("newemail@test.com");
    //     verify(organiserRepository, times(1)).getById(originalOrganiser.getId());
    //     verify(organiserRepository, times(1)).saveAndFlush(originalOrganiser);
        
    // }

    // @Test
    // void testUpdateOrganiser_EmailAlreadyExistsInOrganiser_ThrowAlreadyExistsException() {

    //     // Arrange
    //     String email = "Daniel";
    //     UpdateOrganiserDetailsDTO updateOrganiserDetailsDTO = new UpdateOrganiserDetailsDTO("Jacky", "jacky@yahoo.com.sg", null, 1000);
    //     String exceptionMsg = "";

    //     when(organiserRepository.existsByEmail(anyString()))
    //         .thenReturn(false);
    //     when(organiserRepository.existsByEmail(anyString()))
    //         .thenReturn(true);

    //     // Act
    //     try {
    //         Organiser responseOrganiser = organiserService.updateOrganiser(email, updateOrganiserDetailsDTO);
    //     } catch (AlreadyExistsException e) {
    //         exceptionMsg = e.getMessage();
    //     }

    //     // Assert
    //     assertEquals("Email already exists!", exceptionMsg);
    //     verify(organiserRepository, times(1)).existsByEmail(updateOrganiserDetailsDTO.getEmail());
    //     verify(organiserRepository, times(1)).existsByEmail(updateOrganiserDetailsDTO.getEmail());
    // }
}