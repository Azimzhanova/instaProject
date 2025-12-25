package peaksoft.instaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.instaproject.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {

}
