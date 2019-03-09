package ch.schildj.postcardsender.domain.model.dto;

import ch.schildj.postcardsender.domain.converter.LocalDateTimeSerializer;
import ch.schildj.postcardsender.domain.enums.MessageType;
import ch.schildj.postcardsender.domain.model.RespMessage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

/**
 * Transferobject
 * Response Message
 */
class RespMessageDTO {
    private MessageType type;
    private Integer code;
    private String text;
    private LocalDateTime cdate;

    public RespMessageDTO(MessageType type, Integer code, String text, LocalDateTime cdate) {
        this.type = type;
        this.code = code;
        this.text = text;
        this.cdate = cdate;
    }

    public RespMessageDTO(RespMessage respMessage) {
        this.type = respMessage.getType();
        this.code = respMessage.getCode();
        this.text = respMessage.getText();
        this.cdate = respMessage.getCreation();
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCdate() {
        return cdate;
    }

    public void setCdate(LocalDateTime cdate) {
        this.cdate = cdate;
    }
}
