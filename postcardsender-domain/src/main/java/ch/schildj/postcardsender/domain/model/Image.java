package ch.schildj.postcardsender.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

/**
 * Class Image.
 * Holds every image
 */
@Entity
@Table(name = "IMAGE")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = Image.COLUMN_PREFIX + "ID", unique = true)),
        @AttributeOverride(name = "mdate", column = @Column(name = Image.COLUMN_PREFIX + "MDATE")),
        @AttributeOverride(name = "cdate", column = @Column(name = Image.COLUMN_PREFIX + "CDATE"))
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "getUnusedImages",
                query = "SELECT * " +
                        "FROM IMAGE " +
                        "WHERE IMG_CDATE < ?1 " +
                        "AND NOT EXISTS " +
                        "(SELECT 1 from POSTCARD WHERE PC_FRONT_IMG_ID = IMG_ID) " +
                        "AND NOT EXISTS " +
                        "(SELECT 1 from CAMPAIGN WHERE CAMP_BRAND_IMG_ID = IMG_ID OR CAMP_STAMP_IMG_ID = IMG_ID) " +
                        "AND NOT EXISTS " +
                        "(SELECT 1 from CAMP_IMAGE_SET WHERE CIS_IMG_ID = IMG_ID) ",
                resultClass = Image.class
        )})
public class Image extends AbstractAuditableIdModel implements Serializable {
    protected static final String COLUMN_PREFIX = "IMG_";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "image")
    private java.util.List<CampImageSet> campImageSets;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frontimage")
    private java.util.List<Postcard> postcardList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stamp")
    private java.util.List<Campaign> campaignList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brandingImg")
    private java.util.List<Campaign> campaignBrandingList;

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

    // max 10 MB
    @Column(name = COLUMN_PREFIX + "FILE", nullable = false, length = 10485760)
    @Lob
    private Blob file;

    public List<CampImageSet> getCampImageSets() {
        return campImageSets;
    }

    public void setCampImageSets(List<CampImageSet> campImageSets) {
        this.campImageSets = campImageSets;
    }

    public List<Postcard> getPostcardList() {
        return postcardList;
    }

    public void setPostcardList(List<Postcard> postcardList) {
        this.postcardList = postcardList;
    }

    public List<Campaign> getCampaignList() {
        return campaignList;
    }

    public void setCampaignList(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }

    public Blob getFile() {
        return file;
    }

    public void setFile(Blob file) {
        this.file = file;
    }

    public List<Campaign> getCampaignBrandingList() {
        return campaignBrandingList;
    }

    public void setCampaignBrandingList(List<Campaign> campaignBrandingList) {
        this.campaignBrandingList = campaignBrandingList;
    }
}
