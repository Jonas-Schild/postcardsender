package ch.schildj.postcardsender.apicall.model;

import ch.schildj.postcardsender.apicall.model.apiResponseDto.CodeMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 * Transferobject between the apicall-component and the process-component
 * the class holds the log of the requests with its response
 */

public class RequestLogDto {
    private Long postcardId;
    private String postcardKey;
    private String request;
    private Integer respHttpCode;
    private String respHttpError;
    private ArrayList<CodeMessage> errors;
    private ArrayList<CodeMessage> warnings;
    private LocalDateTime creation;

    public Long getPostcardId() {
        return postcardId;
    }

    public void setPostcardId(Long postcardId) {
        this.postcardId = postcardId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getRespHttpCode() {
        return respHttpCode;
    }

    public void setRespHttpCode(Integer respHttpCode) {
        this.respHttpCode = respHttpCode;
    }

    public String getRespHttpError() {
        return respHttpError;
    }

    public void setRespHttpError(String respHttpError) {
        this.respHttpError = respHttpError;
    }


    public String getPostcardKey() {
        return postcardKey;
    }

    public void setPostcardKey(String postcardKey) {
        this.postcardKey = postcardKey;
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

    public LocalDateTime getCreation() {
        return creation;
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }
}
