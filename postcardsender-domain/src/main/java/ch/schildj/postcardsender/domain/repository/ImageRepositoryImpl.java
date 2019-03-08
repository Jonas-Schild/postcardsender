package ch.schildj.postcardsender.domain.repository;

import ch.schildj.postcardsender.domain.model.Image;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ImageRepositoryImpl implements ImageRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Image> getUnusedImages(LocalDateTime dateTime) {
        List<Image> images = em.createNamedQuery("getUnusedImages", Image.class)
                .setParameter(1, Timestamp.valueOf(dateTime))
                .getResultList();
        return images;
    }

}
