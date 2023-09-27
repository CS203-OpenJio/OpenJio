package G3.jio.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G3.jio.entities.Organiser;

@Repository
public interface OrganiserRepository extends JpaRepository<Organiser, Long> {
    public List<Organiser> findAllByName(String name);

    Optional<Organiser> findByName(String name);

    Optional<Organiser> findById(Long id);

    Optional<Organiser> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(Long id);
}
