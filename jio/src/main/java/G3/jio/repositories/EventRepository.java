package G3.jio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G3.jio.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public List<Event> findAllByName(String eventName);

    boolean existsById(Long id);
}
