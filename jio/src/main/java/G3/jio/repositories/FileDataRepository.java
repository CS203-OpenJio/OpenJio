package G3.jio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import G3.jio.entities.FileData;


public interface FileDataRepository extends JpaRepository<FileData,Integer>{
    Optional<FileData> findByName(String fileName);
}
