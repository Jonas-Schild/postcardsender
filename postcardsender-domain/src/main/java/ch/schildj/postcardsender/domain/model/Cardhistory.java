package ch.schildj.postcardsender.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Class Cardhistory.
 * Holds the requests for every Postcard
 */
@Entity
@Table(name = "CARDHISTORY")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = Cardhistory.COLUMN_PREFIX + "ID", unique = true)),
        @AttributeOverride(name = "mdate", column = @Column(name = Cardhistory.COLUMN_PREFIX + "MDATE")),
        @AttributeOverride(name = "cdate", column = @Column(name = Cardhistory.COLUMN_PREFIX + "CDATE"))
})
public class Cardhistory extends AbstractAuditableIdModel implements Serializable {
    protected static final String COLUMN_PREFIX = "HIST_";

    @ManyToOne()
    @JoinColumns({
            @JoinColumn(
                    name = COLUMN_PREFIX + "PC_ID"
            )
    })
    private Postcard postcard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cardhistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<RespMessage> respMessageList;

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

    @Column(name = COLUMN_PREFIX + "REQUEST", length = 2000)
    private String request;

    @Column(name = COLUMN_PREFIX + "RESP_HTTP_CODE", length = 4)
    private Integer respHttpCode;

    @Column(name = COLUMN_PREFIX + "RESP_HTTP_ERROR_TEXT", length = 2000)
    private String respHttpError;

    public Postcard getPostcard() {
        return postcard;
    }

    public void setPostcard(Postcard postcard) {
        this.postcard = postcard;
    }

    public List<RespMessage> getRespMessageList() {
        return respMessageList;
    }

    public void setRespMessageList(List<RespMessage> respMessageList) {
        this.respMessageList = respMessageList;
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


}
