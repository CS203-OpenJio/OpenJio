package G3.jio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G3.jio.entities.Event;
import G3.jio.entities.EventRegistration;
import G3.jio.entities.Student;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {

    public List<EventRegistration> findAllByStudentId(Long studentId);
    public List<EventRegistration> findAllByEventId(Long eventId);
}
