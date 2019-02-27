package ch.schildj.postcardsender.apicall.model.apiResponseDto;

import java.util.ArrayList;

/**
 * Transferobject to receive the response from the Postcard API of Post CH
 * Image preview
 */
public class PreviewResponse {
    private String cardKey;
    private String fileType;
    private String encoding;
    private String side;
    private String imagedata;
    private ArrayList<CodeMessage> errors;

    public String getCardKey() {
        return cardKey;
    }

    public void setCardKey(String cardKey) {
        this.cardKey = cardKey;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getImagedata() {
        return imagedata;
    }

    public void setImagedata(String imagedata) {
        this.imagedata = imagedata;
    }

    public ArrayList<CodeMessage> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<CodeMessage> errors) {
        this.errors = errors;
    }
}
