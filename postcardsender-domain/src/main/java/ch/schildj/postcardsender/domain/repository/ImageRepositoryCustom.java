package ch.schildj.postcardsender.domain.repository;

import ch.schildj.postcardsender.domain.model.Image;

import java.time.LocalDateTime;
import java.util.List;

public interface ImageRepositoryCustom {

    public List<Image> getUnusedImages(LocalDateTime dateTime) ;
}
