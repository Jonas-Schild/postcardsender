package ch.schildj.postcardsender.domain.repository;

import ch.schildj.postcardsender.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {

    @Query("SELECT i FROM Image i where i.cdate < :border")
    List<Image> findAllUntil(@Param("border") LocalDateTime border);


}
