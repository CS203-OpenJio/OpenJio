package G3.jio.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G3.jio.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    public List<Student> findAllByName(String name);

    Optional<Student> findByName(String name);

    Optional<Student> findById(Long id);

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    boolean existsByName(String name);
}
