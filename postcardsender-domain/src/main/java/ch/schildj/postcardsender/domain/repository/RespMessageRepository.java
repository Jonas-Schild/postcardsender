package ch.schildj.postcardsender.domain.repository;

import ch.schildj.postcardsender.domain.model.RespMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespMessageRepository extends JpaRepository<RespMessage, Long> {


}
