package ch.schildj.postcardsender.domain.specification;

import ch.schildj.postcardsender.domain.enums.PostcardState;
import ch.schildj.postcardsender.domain.enums.TransmissionState;
import ch.schildj.postcardsender.domain.model.Postcard;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * JPA Specification-Class for Postcards to add several restrictions
 */

public class PostcardSpec {


    private PostcardSpec() {
    }

    public static Specification<Postcard> isFromCampaign(Long campId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("campaign"), campId);
    }

    public static Specification<Postcard> hasState(PostcardState state) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), state);
    }

    public static Specification<Postcard> hasTransmissionState(TransmissionState transmissionState) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("transmissionState"), transmissionState);
    }

    public static Specification<Postcard> hasIpAddress(String ip) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("ipAddress"), ip);
    }

    public static Specification<Postcard> isDateAfter(LocalDateTime date) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThan(root.<LocalDateTime>get("cdate"), date);
    }

    public static Specification<Postcard> isDateBefore(LocalDateTime date) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThan(root.<LocalDateTime>get("cdate"), date);
    }
}