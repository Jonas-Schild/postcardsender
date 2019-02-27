package ch.schildj.postcardsender.domain.model;

import ch.schildj.postcardsender.domain.converter.EnumConverters;
import ch.schildj.postcardsender.domain.enums.MessageType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class RespMessage.
 */
@Entity
@Table(name = "RESP_MESSAGE")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = RespMessage.COLUMN_PREFIX + "ID", unique = true)),
        @AttributeOverride(name = "mdate", column = @Column(name = RespMessage.COLUMN_PREFIX + "MDATE")),
        @AttributeOverride(name = "cdate", column = @Column(name = RespMessage.COLUMN_PREFIX + "CDATE"))
})
public class RespMessage extends AbstractAuditableIdModel implements Serializable {
    protected static final String COLUMN_PREFIX = "MSG_";

    @ManyToOne()
    @JoinColumns({
            @JoinColumn(
                    name = COLUMN_PREFIX + "HIST_ID"
            )
    })
    private Cardhistory cardhistory;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = COLUMN_PREFIX + "_ID_GENERATOR")
    @SequenceGenerator(name = COLUMN_PREFIX + "_ID_GENERATOR", sequenceName = COLUMN_PREFIX + "_ID_GENERATOR_SEQ", initialValue = 10000)
    @Column(name = COLUMN_PREFIX + "ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = COLUMN_PREFIX + "TYPE")
    @Convert(converter = EnumConverters.MessageTypeEnumConverter.class)
    private MessageType type;

    @Column(name = COLUMN_PREFIX + "CODE", length = 10)
    private Integer code;

    @Column(name = COLUMN_PREFIX + "TEXT", length = 500)
    private String text;

    public RespMessage(Cardhistory cardhistory, MessageType type, Integer code, String text) {
        this.cardhistory = cardhistory;
        this.type = type;
        this.code = code;
        this.text = text;
    }


    public Cardhistory getCardhistory() {
        return cardhistory;
    }

    public void setCardhistory(Cardhistory cardhistory) {
        this.cardhistory = cardhistory;
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
}
