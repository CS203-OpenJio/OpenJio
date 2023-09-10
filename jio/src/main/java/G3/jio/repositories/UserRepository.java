package G3.jio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import G3.jio.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findAllByName(String name);
}
