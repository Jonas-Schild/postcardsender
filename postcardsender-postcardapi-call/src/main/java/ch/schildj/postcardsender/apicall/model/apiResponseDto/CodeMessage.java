package ch.schildj.postcardsender.apicall.model.apiResponseDto;

/**
 * Transferobject to receive the response from the Postcard API of Post CH
 * Answer-Codes
 */
public class CodeMessage {
    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

