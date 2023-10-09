package G3.jio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import G3.jio.entities.FileData;
import G3.jio.entities.ImageData;

import java.util.Optional;


public interface FileDataRepository extends JpaRepository<FileData,Integer>{
    Optional<FileData> findByName(String fileName);
}
