package ch.schildj.postcardsender.domain.enums;

/**
 * Branding Type
 */
public enum BrandingType {

    IMAGE(0),
    TEXT(1),
    QR(2);


    private final Integer brandingType;

    BrandingType(Integer brandingType) {
        this.brandingType = brandingType;
    }


    public Integer getCode() {
        return brandingType;
    }

    public Integer toInt() {
        return brandingType;
    }

}
