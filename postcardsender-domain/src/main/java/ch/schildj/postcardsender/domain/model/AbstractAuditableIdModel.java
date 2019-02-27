/*
 * ------------------------------------------------------------------------------------------------
 * Copyright 2015 by Swiss Post, Information Technology Services
 * ------------------------------------------------------------------------------------------------
 * $Id: $
 * ------------------------------------------------------------------------------------------------
 */

package ch.schildj.postcardsender.domain.model;

import ch.schildj.postcardsender.domain.converter.LocalDateTimeAttributeConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


/**
 * Adds mutations and creation-Date to every entity
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditableIdModel {
    protected static final String COLUMN_PREFIX = "TCH_";


    @Column(name = COLUMN_PREFIX + "MDATE", nullable = false)
    @LastModifiedDate
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime mdate;

    @Column(name = COLUMN_PREFIX + "CDATE", nullable = false)
    @CreatedDate
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime cdate;

    protected AbstractAuditableIdModel() {
    }


    @Convert(converter = LocalDateTimeAttributeConverter.class)
    public LocalDateTime getLastModification() {
        return this.mdate;
    }

    public void setLastModification(LocalDateTime lastModification) {
        this.mdate = lastModification;
    }

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    public LocalDateTime getCreation() {
        return this.cdate;
    }

    public void setCreation(LocalDateTime creation) {
        this.cdate = creation;
    }


}
