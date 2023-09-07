package G3.jio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G3.jio.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
