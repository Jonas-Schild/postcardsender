package ch.schildj.postcardsender.domain.repository;

import ch.schildj.postcardsender.domain.model.Image;
import ch.schildj.postcardsender.domain.model.Postcard;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class PostcardRepositoryImpl implements PostcardRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    /*
     * get latest version of the entity
     */
    @Override
    public void refresh(Postcard postcard) {
        em.refresh(postcard);
    }

}
