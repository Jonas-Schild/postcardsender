package ch.schildj.postcardsender.domain.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class CampImageSet.
 * Defines Images available for a campaign
 */
@Entity
@Table(name = "CAMP_IMAGE_SET")
@AttributeOverrides({
        @AttributeOverride(name = "mdate", column = @Column(name = CampImageSet.COLUMN_PREFIX + "MDATE")),
        @AttributeOverride(name = "cdate", column = @Column(name = CampImageSet.COLUMN_PREFIX + "CDATE"))
})
public class CampImageSet extends AbstractAuditableIdModel implements Serializable {
    protected static final String COLUMN_PREFIX = "CIS_";


    @ManyToOne()
    @JoinColumns({
            @JoinColumn(
                    name = COLUMN_PREFIX + "CAMP_ID"
            )
    })
    private Campaign campaign;


    @ManyToOne()
    @JoinColumns({
            @JoinColumn(
                    name = COLUMN_PREFIX + "IMG_ID"
            )
    })
    private Image image;

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

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
