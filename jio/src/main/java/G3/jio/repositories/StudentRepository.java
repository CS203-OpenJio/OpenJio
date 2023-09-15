package G3.jio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G3.jio.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    public List<Student> findAllByName(String name);
}
