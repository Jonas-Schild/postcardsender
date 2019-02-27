package ch.schildj.postcardsender.apicall.model.apiResponseDto;

import ch.schildj.postcardsender.apicall.util.LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

/**
 * Transferobject to receive the response from the Postcard API of Post CH
 * Postcard state
 */
public class State {
    private String state;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
