package ch.schildj.postcardsender.domain.model.dto;

import ch.schildj.postcardsender.domain.converter.CustLocalDateTimeDeserializer;
import ch.schildj.postcardsender.domain.enums.PostcardState;
import ch.schildj.postcardsender.domain.enums.TransmissionState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

/**
 * Transferobject
 * This class contains some query-parameter for the search of postcards
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardSearchDTO {
    private LocalDateTime from;
    private LocalDateTime till;
    private PostcardState state;
    private TransmissionState transmissionState;

    public LocalDateTime getFrom() {
        return from;
    }

    @JsonDeserialize(using = CustLocalDateTimeDeserializer.class)
    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTill() {
        return till;
    }

    @JsonDeserialize(using = CustLocalDateTimeDeserializer.class)
    public void setTill(LocalDateTime till) {
        this.till = till;
    }

    public PostcardState getState() {
        return state;
    }

    public void setState(PostcardState state) {
        this.state = state;
    }

    public TransmissionState getTransmissionState() {
        return transmissionState;
    }

    public void setTransmissionState(TransmissionState transmissionState) {
        this.transmissionState = transmissionState;
    }
}
