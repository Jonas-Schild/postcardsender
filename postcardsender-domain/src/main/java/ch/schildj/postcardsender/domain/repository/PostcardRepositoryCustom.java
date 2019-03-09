package ch.schildj.postcardsender.domain.repository;

import ch.schildj.postcardsender.domain.model.Postcard;

public interface PostcardRepositoryCustom {

    void refresh(Postcard postcard);
}
