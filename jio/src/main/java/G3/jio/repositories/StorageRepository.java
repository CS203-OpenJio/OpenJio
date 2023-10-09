package G3.jio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import G3.jio.entities.ImageData;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageData,Long> {


    Optional<ImageData> findByName(String fileName);
}