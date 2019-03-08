package ch.schildj.postcardsender.domain.repository;

import ch.schildj.postcardsender.domain.model.Postcard;
import ch.schildj.postcardsender.domain.model.dto.PostcardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostcardRepository extends JpaRepository<Postcard, Long>, JpaSpecificationExecutor<Postcard> {

    @Query("SELECT p FROM Postcard p WHERE p.campaign.id = :campId order by p.cdate DESC")
    Page<PostcardDTO> findAllByCampaign(@Param("campId") Long campId, Pageable pageable);

    @Query("SELECT p FROM Postcard p WHERE p.status = ch.schildj.postcardsender.domain.enums.PostcardState.OPEN and p.cdate < :dateTime")
    List<Postcard> findUnapproved(@Param("dateTime") LocalDateTime dateTime);

}
