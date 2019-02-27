package ch.schildj.postcardsender.domain.enums;

/**
 * Postcard State (internal)
 */
public enum PostcardState {

    OPEN(0),
    APPROVED(1);


    private final Integer postcardState;

    PostcardState(Integer postcardState) {
        this.postcardState = postcardState;
    }


    public Integer getCode() {
        return postcardState;
    }

    public Integer toInt() {
        return postcardState;
    }

}
