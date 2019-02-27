package ch.schildj.postcardsender.apicall.model.apiResponseDto;

import java.util.ArrayList;

/**
 * Transferobject to receive the response from the Postcard API of Post CH
 * State of Postcard
 */
public class PostcardStateResponse {
    private String cardKey;
    private State state;
    private ArrayList<CodeMessage> warnings;

    public String getCardKey() {
        return cardKey;
    }

    public void setCardKey(String cardKey) {
        this.cardKey = cardKey;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ArrayList<CodeMessage> getWarnings() {
        return warnings;
    }

    public void setWarnings(ArrayList<CodeMessage> warnings) {
        this.warnings = warnings;
    }
}
