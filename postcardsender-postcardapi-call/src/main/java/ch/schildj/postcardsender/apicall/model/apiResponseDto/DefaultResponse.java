package ch.schildj.postcardsender.apicall.model.apiResponseDto;

import java.util.ArrayList;

/**
 * Transferobject to receive the response from the Postcard API of Post CH
 * Default response -Element
 */
public class DefaultResponse {
    private String cardKey;
    private String successMessage;
    private ArrayList<CodeMessage> errors;
    private ArrayList<CodeMessage> warnings;


    public String getCardKey() {
        return cardKey;
    }

    public void setCardKey(String cardKey) {
        this.cardKey = cardKey;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public ArrayList<CodeMessage> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<CodeMessage> errors) {
        this.errors = errors;
    }

    public ArrayList<CodeMessage> getWarnings() {
        return warnings;
    }

    public void setWarnings(ArrayList<CodeMessage> warnings) {
        this.warnings = warnings;
    }
}
