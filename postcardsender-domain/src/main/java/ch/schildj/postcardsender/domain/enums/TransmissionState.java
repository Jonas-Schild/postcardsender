package ch.schildj.postcardsender.domain.enums;

/**
 * Transmission State to Post CH
 */
public enum TransmissionState {

    OPEN(0),
    OK(1),
    WARNING(2),
    ERROR(3);


    private final Integer transmissionState;

    TransmissionState(Integer transmissionState) {
        this.transmissionState = transmissionState;
    }


    public Integer getCode() {
        return transmissionState;
    }

    public Integer toInt() {
        return transmissionState;
    }

}
