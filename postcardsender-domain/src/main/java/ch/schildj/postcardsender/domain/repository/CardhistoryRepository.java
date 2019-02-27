package ch.schildj.postcardsender.domain.repository;

import ch.schildj.postcardsender.domain.model.Cardhistory;
import ch.schildj.postcardsender.domain.model.dto.CardhistoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardhistoryRepository extends JpaRepository<Cardhistory, Long> {

    @Query("SELECT ch FROM Cardhistory ch WHERE ch.postcard.id = :id order by ch.cdate DESC")
    List<CardhistoryDTO> findAllByPostcard(@Param("id") Long id);

}
