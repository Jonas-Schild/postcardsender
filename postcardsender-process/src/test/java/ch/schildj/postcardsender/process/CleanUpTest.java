package ch.schildj.postcardsender.process;

import ch.schildj.config.TestProcessConfiguration;
import ch.schildj.postcardsender.domain.enums.BrandingType;
import ch.schildj.postcardsender.domain.enums.ImageType;
import ch.schildj.postcardsender.domain.enums.PostcardState;
import ch.schildj.postcardsender.domain.enums.TransmissionState;
import ch.schildj.postcardsender.domain.model.Campaign;
import ch.schildj.postcardsender.domain.model.Image;
import ch.schildj.postcardsender.domain.model.Postcard;
import ch.schildj.postcardsender.domain.repository.CampaignRepository;
import ch.schildj.postcardsender.domain.repository.ImageRepository;
import ch.schildj.postcardsender.domain.repository.PostcardRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestProcessConfiguration.class, CleanUp.class})
@TestPropertySource(locations = "classpath:/config/process/application-unittest.properties")
public class CleanUpTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    CleanUp cleanUp;

    @Autowired
    PostcardRepository postcardRepository;

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    ImageRepository imageRepository;

    private Campaign campaign;

    @Before
    public void setUp() throws Exception {
        this.campaign = new Campaign();
        this.campaign.setKey("26d121ad-364b-4977-9271-6ac3fbacb6fb");
        this.campaign.setDesc("Testkampagne");
        this.campaign.setImgType(ImageType.PREDEFINED);
        this.campaign.setMaxCards(1000);
        this.campaign.setValidFrom(LocalDate.of(2019, 01, 01));
        this.campaign.setValidTo(LocalDate.of(2022, 12, 31));
        this.campaign.setBrandText("Merci");
        this.campaign.setBrandType(BrandingType.TEXT);
        campaignRepository.saveAndFlush(this.campaign);

    }

    @After
    public void tearDown() throws Exception {
        campaignRepository.delete(this.campaign);
    }

    private Image createFromUrl(URL url) throws Exception {

        BufferedImage originalImage = ImageIO.read(new File(url.toURI()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        SerialBlob sb = new SerialBlob(imageInByte);
        Image img = new Image();
        img.setFile(sb);
        return img;
    }


    @Test
    @Rollback
    public void deleteUnusedImages() throws Exception {

        URL url = this.getClass().getResource("/images/testbildPostkarte.png");
        URL urlStamp = this.getClass().getResource("/images/testbildBriefmarke.png");

        // not used, should be deleted
        Image img1 = this.createFromUrl(url);
        imageRepository.save(img1);
        img1.setCreation(LocalDateTime.now().minusDays(2));
        imageRepository.save(img1);

        // used for postcard
        Image img2 = this.createFromUrl(url);
        imageRepository.save(img2);
        img2.setCreation(LocalDateTime.now().minusDays(2));
        imageRepository.save(img2);

        // not used but creation now
        Image img3 = this.createFromUrl(url);
        imageRepository.save(img3);

        // used for stamp
        Image stamp = this.createFromUrl(urlStamp);
        imageRepository.save(stamp);
        stamp.setCreation(LocalDateTime.now().minusDays(2));
        imageRepository.save(stamp);

        campaign.setStamp(stamp);
        campaignRepository.saveAndFlush(campaign);

        Postcard p = new Postcard();
        p.setTransmissionState(TransmissionState.OPEN);
        p.setStatus(PostcardState.APPROVED);

        p.setText("Hallo Empfänger");
        p.setCampaign(campaign);
        p.setSenderFirstname("Hans");
        p.setSenderLastname("Muster");
        p.setSenderStreet("Bernstrasse");
        p.setSenderHousenr("180");
        p.setSenderZip("8000");
        p.setSenderCity("Zürich");
        p.setRecipientFirstname("Nicole");
        p.setRecipientLastname("Schweizer");
        p.setRecipientStreet("Bundesplatz");
        p.setRecipientHousenr("1a");
        p.setRecipientZip("3000");
        p.setRecipientCity("Bern");
        p.setFrontimage(img2);

        postcardRepository.saveAndFlush(p);
        imageRepository.flush();

        cleanUp.deleteUnusedImages();

        List<Image> images = imageRepository.findAll();
        Assert.assertEquals(3, images.size());

    }


    @Test
    @Rollback
    public void deleteUnapprovedCards() throws Exception {


        Postcard p = new Postcard();
        p.setTransmissionState(TransmissionState.OPEN);
        p.setStatus(PostcardState.APPROVED);

        p.setText("Hallo Empfänger");
        p.setCampaign(campaign);
        p.setSenderFirstname("Hans");
        p.setSenderLastname("Muster");
        p.setSenderStreet("Bernstrasse");
        p.setSenderHousenr("180");
        p.setSenderZip("8000");
        p.setSenderCity("Zürich");
        p.setRecipientFirstname("Nicole");
        p.setRecipientLastname("Schweizer");
        p.setRecipientStreet("Bundesplatz");
        p.setRecipientHousenr("1a");
        p.setRecipientZip("3000");
        p.setRecipientCity("Bern");

        postcardRepository.save(p);
        p.setCreation(LocalDateTime.now().minusDays(2));
        postcardRepository.save(p);

        p = new Postcard();
        p.setTransmissionState(TransmissionState.OPEN);
        p.setStatus(PostcardState.OPEN);

        p.setText("Hallo Empfänger");
        p.setCampaign(campaign);
        p.setSenderFirstname("Hans");
        p.setSenderLastname("Muster");
        p.setSenderStreet("Bernstrasse");
        p.setSenderHousenr("180");
        p.setSenderZip("8000");
        p.setSenderCity("Zürich");
        p.setRecipientFirstname("Nicole");
        p.setRecipientLastname("Schweizer");
        p.setRecipientStreet("Bundesplatz");
        p.setRecipientHousenr("1a");
        p.setRecipientZip("3000");
        p.setRecipientCity("Bern");

        postcardRepository.save(p);
        p.setCreation(LocalDateTime.now().minusDays(2));
        postcardRepository.saveAndFlush(p);

        cleanUp.deleteUnapprovedCards();

        List<Postcard> postcards = postcardRepository.findAll();
        Assert.assertEquals(1, postcards.size());

    }


}