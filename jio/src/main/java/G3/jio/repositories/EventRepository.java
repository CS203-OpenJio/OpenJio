package G3.jio.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G3.jio.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public List<Event> findAllByName(String eventName);

    boolean existsById(Long id);

    public List<Event> findAllByOrganiserId(Long id);

    public Optional<Event> findByNameAndStartDateTime(String name, LocalDateTime startDateTime);
}
