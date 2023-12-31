package G3.jio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G3.jio.entities.EventRegistration;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {

    public List<EventRegistration> findAllByStudentId(Long studentId);
    public List<EventRegistration> findAllByEventId(Long eventId);
    public boolean existsByStudentIdAndEventId(Long studentId, Long eventId);
    public EventRegistration getReferenceByEventIdAndStudentId(Long eventId, Long studentId);

}
