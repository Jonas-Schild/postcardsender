package ch.schildj.postcardsender.domain.repository;

import ch.schildj.postcardsender.domain.model.CampImageSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampImageSetRepository extends JpaRepository<CampImageSet, Long> {

    @Query("SELECT cs.image.id FROM CampImageSet cs WHERE cs.campaign.id = :id ")
    List<Long> findCampImageSetByCampaign(@Param("id") Long id);

    @Query("SELECT cs FROM CampImageSet cs WHERE cs.campaign.id = :campId AND cs.image.id = :imageId")
    List<CampImageSet> findAllByCampIdAndImageId(@Param("campId") Long campId, @Param("imageId") Long imageId);


}
