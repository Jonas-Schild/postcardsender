package ch.schildj.postcardsender.domain.enums;

/**
 * Message type
 */
public enum MessageType {

    WARNING(1),
    ERROR(9);


    private final Integer messageType;

    MessageType(Integer messageType) {
        this.messageType = messageType;
    }


    public Integer getCode() {
        return messageType;
    }

    public Integer toInt() {
        return messageType;
    }

}
