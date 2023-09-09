package G3.jio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import G3.jio.entities.Events;

public interface EventsRepository extends JpaRepository<Events, Long> {

    public List<Events> findAllByName();
    
}
