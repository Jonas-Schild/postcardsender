package ch.schildj.postcardsender.domain.model;

import ch.schildj.postcardsender.domain.converter.EnumConverters;
import ch.schildj.postcardsender.domain.enums.PostcardState;
import ch.schildj.postcardsender.domain.enums.TransmissionState;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Class postcard.
 */
@Entity
@DynamicUpdate
@Table(name = "POSTCARD")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = Postcard.COLUMN_PREFIX + "ID", unique = true)),
        @AttributeOverride(name = "mdate", column = @Column(name = Postcard.COLUMN_PREFIX + "MDATE")),
        @AttributeOverride(name = "cdate", column = @Column(name = Postcard.COLUMN_PREFIX + "CDATE"))
})
public class Postcard extends AbstractAuditableIdModel implements Serializable {
    protected static final String COLUMN_PREFIX = "PC_";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(
                    name = COLUMN_PREFIX + "FRONT_IMG_ID"
            )
    })
    private Image frontimage;


    @ManyToOne()
    @JoinColumns({
            @JoinColumn(
                    name = COLUMN_PREFIX + "CAMP_ID"
            )
    })
    private Campaign campaign;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "postcard", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Cardhistory> cardhistoryList;

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

    @Column(name = COLUMN_PREFIX + "KEY")
    private String key;

    @Length(min = 1, max = 900)
    @Column(name = COLUMN_PREFIX + "TEXT", length = 900)
    private String text;

    @Length(min = 2, max = 30)
    @Column(name = COLUMN_PREFIX + "SENDER_FIRSTNAME", length = 30)
    private String senderFirstname;

    @Length(min = 2, max = 30)
    @Column(name = COLUMN_PREFIX + "SENDER_LASTNAME", length = 30)
    private String senderLastname;

    @Length(min = 2, max = 50)
    @Column(name = COLUMN_PREFIX + "SENDER_STREET", length = 50)
    private String senderStreet;

    @Length(max = 5)
    @Column(name = COLUMN_PREFIX + "SENDER_HOUSENR", length = 5)
    private String senderHousenr;

    @Length(min = 4, max = 6)
    @Column(name = COLUMN_PREFIX + "SENDER_ZIP", length = 6)
    private String senderZip;

    @Length(min = 2, max = 30)
    @Column(name = COLUMN_PREFIX + "SENDER_CITY", length = 30)
    private String senderCity;

    @Length(min = 2, max = 30)
    @Column(name = COLUMN_PREFIX + "RECIPIENT_FIRSTNAME", length = 30)
    private String recipientFirstname;

    @Length(min = 2, max = 30)
    @Column(name = COLUMN_PREFIX + "RECIPIENT_LASTNAME", length = 30)
    private String recipientLastname;

    @Length(min = 2, max = 50)
    @Column(name = COLUMN_PREFIX + "RECIPIENT_STREET", length = 50)
    private String recipientStreet;

    @Length(max = 5)
    @Column(name = COLUMN_PREFIX + "RECIPIENT_HOUSENR", length = 5)
    private String recipientHousenr;

    @Length(min = 4, max = 6)
    @Column(name = COLUMN_PREFIX + "RECIPIENT_ZIP", length = 6)
    private String recipientZip;

    @Length(min = 2, max = 30)
    @Column(name = COLUMN_PREFIX + "RECIPIENT_CITY", length = 30)
    private String recipientCity;

    @Column(name = COLUMN_PREFIX + "STATE", nullable = false)
    @Convert(converter = EnumConverters.PostcardStateEnumConverter.class)
    private PostcardState status;

    @Column(name = COLUMN_PREFIX + "TRANSMISSION_STATE", nullable = false)
    @Convert(converter = EnumConverters.TransmissionStateEnumConverter.class)
    private TransmissionState transmissionState;

    @Column(name = COLUMN_PREFIX + "CREATION_IP")
    private String ipAddress;

    public Image getFrontimage() {
        return frontimage;
    }

    public void setFrontimage(Image frontimage) {
        this.frontimage = frontimage;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public List<Cardhistory> getCardhistoryList() {
        return cardhistoryList;
    }

    public void setCardhistoryList(List<Cardhistory> cardhistoryList) {
        this.cardhistoryList = cardhistoryList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderFirstname() {
        return senderFirstname;
    }

    public void setSenderFirstname(String senderFirstname) {
        this.senderFirstname = senderFirstname;
    }

    public String getSenderLastname() {
        return senderLastname;
    }

    public void setSenderLastname(String senderLastname) {
        this.senderLastname = senderLastname;
    }

    public String getSenderStreet() {
        return senderStreet;
    }

    public void setSenderStreet(String senderStreet) {
        this.senderStreet = senderStreet;
    }

    public String getSenderHousenr() {
        return senderHousenr;
    }

    public void setSenderHousenr(String senderHousenr) {
        this.senderHousenr = senderHousenr;
    }

    public String getSenderZip() {
        return senderZip;
    }

    public void setSenderZip(String senderZip) {
        this.senderZip = senderZip;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getRecipientFirstname() {
        return recipientFirstname;
    }

    public void setRecipientFirstname(String recipientFirstname) {
        this.recipientFirstname = recipientFirstname;
    }

    public String getRecipientLastname() {
        return recipientLastname;
    }

    public void setRecipientLastname(String recipientLastname) {
        this.recipientLastname = recipientLastname;
    }

    public String getRecipientStreet() {
        return recipientStreet;
    }

    public void setRecipientStreet(String recipientStreet) {
        this.recipientStreet = recipientStreet;
    }

    public String getRecipientHousenr() {
        return recipientHousenr;
    }

    public void setRecipientHousenr(String recipientHousenr) {
        this.recipientHousenr = recipientHousenr;
    }

    public String getRecipientZip() {
        return recipientZip;
    }

    public void setRecipientZip(String recipientZip) {
        this.recipientZip = recipientZip;
    }

    public String getRecipientCity() {
        return recipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public PostcardState getStatus() {
        return status;
    }

    public void setStatus(PostcardState status) {
        this.status = status;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public TransmissionState getTransmissionState() {
        return transmissionState;
    }

    public void setTransmissionState(TransmissionState transmissionState) {
        this.transmissionState = transmissionState;
    }
}