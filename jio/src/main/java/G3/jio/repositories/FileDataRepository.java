package G3.jio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import G3.jio.entities.FileData;
import java.util.List;



public interface FileDataRepository extends JpaRepository<FileData,Long>{
    Optional<FileData> findByName(String fileName);
    Optional<FileData> findById(Long id);
}
