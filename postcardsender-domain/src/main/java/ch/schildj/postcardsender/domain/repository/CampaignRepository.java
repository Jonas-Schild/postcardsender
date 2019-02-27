package ch.schildj.postcardsender.domain.repository;

import ch.schildj.postcardsender.domain.model.Campaign;
import ch.schildj.postcardsender.domain.model.dto.CampaignDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {


    @Query("SELECT c FROM Campaign c WHERE c.validFrom <=  :targetdate and c.validTo >= :targetdate ")
    List<CampaignDTO> findAllActive(@Param("targetdate") LocalDate targetdate);

    @Query("SELECT c FROM Campaign c")
    List<CampaignDTO> findAllDTO();

    CampaignDTO findFirstByKey(String key);


}
