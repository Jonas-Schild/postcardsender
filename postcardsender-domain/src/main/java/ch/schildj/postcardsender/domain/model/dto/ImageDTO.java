package ch.schildj.postcardsender.domain.model.dto;

import ch.schildj.postcardsender.domain.model.Image;

import java.sql.Blob;

/**
 * Transferobject
 * Image
 */
public class ImageDTO {
    private Blob file;

    public ImageDTO(Blob file) {
        this.file = file;
    }

    public ImageDTO(Image image) {
        this.file = image.getFile();
    }
}
