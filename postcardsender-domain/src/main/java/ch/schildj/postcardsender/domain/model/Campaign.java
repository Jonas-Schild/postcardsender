package ch.schildj.postcardsender.domain.model;

import ch.schildj.postcardsender.domain.converter.EnumConverters;
import ch.schildj.postcardsender.domain.converter.LocalDateAttributeConverter;
import ch.schildj.postcardsender.domain.enums.BrandingType;
import ch.schildj.postcardsender.domain.enums.ImageType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Class campaign.
 */
@Entity
@Table(name = "CAMPAIGN")
@AttributeOverrides({
        @AttributeOverride(name = "mdate", column = @Column(name = Campaign.COLUMN_PREFIX + "MDATE")),
        @AttributeOverride(name = "cdate", column = @Column(name = Campaign.COLUMN_PREFIX + "CDATE"))
})
public class Campaign extends AbstractAuditableIdModel implements Serializable {
    protected static final String COLUMN_PREFIX = "CAMP_";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(
                    name = COLUMN_PREFIX + "STAMP_IMG_ID"
            )
    })
    private Image stamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(
                    name = COLUMN_PREFIX + "BRAND_IMG_ID"
            )
    })
    private Image brandingImg;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<CampImageSet> campImageSetList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Postcard> postcardList;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = COLUMN_PREFIX + "_ID_GENERATOR")
    @SequenceGenerator(name = COLUMN_PREFIX + "_ID_GENERATOR", sequenceName = COLUMN_PREFIX + "_ID_GENERATOR_SEQ", initialValue = 10000)
    @Column(name = COLUMN_PREFIX + "ID")
    private Long id;


    @Column(name = COLUMN_PREFIX + "KEY", nullable = false, unique = true)
    private String key;

    @Column(name = COLUMN_PREFIX + "DESC", length = 500)
    private String desc;

    @Column(name = COLUMN_PREFIX + "FROM", nullable = false)
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate validFrom;

    @Column(name = COLUMN_PREFIX + "TO", nullable = false)
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate validTo;

    @Column(name = COLUMN_PREFIX + "IMG_TYPE", nullable = false)
    @Convert(converter = EnumConverters.ImageTypeEnumConverter.class)
    private ImageType imgType;


    @Column(name = COLUMN_PREFIX + "MAX_CARDS", nullable = false)
    private Integer maxCards;

    @Column(name = COLUMN_PREFIX + "BRAND_TYPE", nullable = false)
    @Convert(converter = EnumConverters.BrandingTypeEnumConverter.class)
    private BrandingType brandType;


    @Column(name = COLUMN_PREFIX + "BRAND_TEXT", length = 250)
    private String brandText;

    @Column(name = COLUMN_PREFIX + "BRAND_BLOCK_COLOR", length = 20)
    private String brandBlockColor;

    @Column(name = COLUMN_PREFIX + "BRAND_TEXT_COLOR", length = 20)
    private String brandTextColor;

    @Column(name = COLUMN_PREFIX + "BRAND_QR", length = 100)
    private String brandQr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public ImageType getImgType() {
        return imgType;
    }

    public void setImgType(ImageType imgType) {
        this.imgType = imgType;
    }

    public Integer getMaxCards() {
        return maxCards;
    }

    public void setMaxCards(Integer maxCards) {
        this.maxCards = maxCards;
    }

    public BrandingType getBrandType() {
        return brandType;
    }

    public void setBrandType(BrandingType brandType) {
        this.brandType = brandType;
    }

    public String getBrandText() {
        return brandText;
    }

    public void setBrandText(String brandText) {
        this.brandText = brandText;
    }

    public String getBrandBlockColor() {
        return brandBlockColor;
    }

    public void setBrandBlockColor(String brandBlockColor) {
        this.brandBlockColor = brandBlockColor;
    }

    public String getBrandTextColor() {
        return brandTextColor;
    }

    public void setBrandTextColor(String brandTextColor) {
        this.brandTextColor = brandTextColor;
    }

    public String getBrandQr() {
        return brandQr;
    }

    public void setBrandQr(String brandQr) {
        this.brandQr = brandQr;
    }

    public Image getStamp() {
        return stamp;
    }

    public void setStamp(Image stamp) {
        this.stamp = stamp;
    }

    public List<CampImageSet> getCampImageSetList() {
        return campImageSetList;
    }

    public void setCampImageSetList(List<CampImageSet> campImageSetList) {
        this.campImageSetList = campImageSetList;
    }

    public List<Postcard> getPostcardList() {
        return postcardList;
    }

    public void setPostcardList(List<Postcard> postcardList) {
        this.postcardList = postcardList;
    }

    public Image getBrandingImg() {
        return brandingImg;
    }

    public void setBrandingImg(Image brandingImg) {
        this.brandingImg = brandingImg;
    }
}
