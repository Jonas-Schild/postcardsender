package ch.schildj.postcardsender.domain.enums;

/**
 * Image Type
 */
public enum ImageType {

    UPLOAD(1),
    PREDEFINED(2);


    private final Integer imageType;

    ImageType(Integer imageType) {
        this.imageType = imageType;
    }


    public Integer getCode() {
        return imageType;
    }

    public Integer toInt() {
        return imageType;
    }

}
