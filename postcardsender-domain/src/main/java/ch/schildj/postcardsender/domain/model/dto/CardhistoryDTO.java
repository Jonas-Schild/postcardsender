package ch.schildj.postcardsender.domain.model.dto;

import ch.schildj.postcardsender.domain.converter.LocalDateTimeSerializer;
import ch.schildj.postcardsender.domain.model.Cardhistory;
import ch.schildj.postcardsender.domain.model.RespMessage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Transferobject
 * History of Postcard
 */
public class CardhistoryDTO {

    private String request;
    private Integer respHttpCode;
    private String respHttpError;
    private LocalDateTime requestDate;
    private List<RespMessageDTO> respMessageDTOS;


    public CardhistoryDTO() {

    }


    public CardhistoryDTO(Cardhistory cardhistory) {
        this.request = cardhistory.getRequest();
        this.respHttpCode = cardhistory.getRespHttpCode();
        this.respHttpError = cardhistory.getRespHttpError();
        this.requestDate = cardhistory.getCreation();

        if (cardhistory.getRespMessageList() != null) {
            this.respMessageDTOS = this.createDtoList(cardhistory.getRespMessageList());
        }
    }


    public CardhistoryDTO(String request, Integer respHttpCode, String respHttpError, LocalDateTime requestDate, List<RespMessage> respMessages) {
        this.request = request;
        this.respHttpCode = respHttpCode;
        this.respHttpError = respHttpError;
        this.requestDate = requestDate;

        if (respMessages != null) {
            this.respMessageDTOS = this.createDtoList(respMessages);
        }
    }


    public static List<RespMessageDTO> createDtoList(List<RespMessage> respMessages) {

        List<RespMessageDTO> dtoList = new ArrayList<RespMessageDTO>();

        for (RespMessage respMessage : respMessages) {
            dtoList.add(new RespMessageDTO(respMessage));
        }

        return dtoList;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public List<RespMessageDTO> getRespMessageDTOS() {
        return respMessageDTOS;
    }

    public void setRespMessageDTOS(List<RespMessageDTO> respMessageDTOS) {
        this.respMessageDTOS = respMessageDTOS;
    }
}
