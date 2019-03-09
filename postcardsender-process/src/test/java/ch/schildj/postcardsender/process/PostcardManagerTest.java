package ch.schildj.postcardsender.process;

import ch.schildj.config.TestProcessConfiguration;
import ch.schildj.postcardsender.domain.enums.BrandingType;
import ch.schildj.postcardsender.domain.enums.ImageType;
import ch.schildj.postcardsender.domain.enums.PostcardState;
import ch.schildj.postcardsender.domain.enums.TransmissionState;
import ch.schildj.postcardsender.domain.model.Campaign;
import ch.schildj.postcardsender.domain.model.Image;
import ch.schildj.postcardsender.domain.model.Postcard;
import ch.schildj.postcardsender.domain.model.dto.PostcardDTO;
import ch.schildj.postcardsender.domain.repository.CampaignRepository;
import ch.schildj.postcardsender.domain.repository.ImageRepository;
import ch.schildj.postcardsender.domain.repository.PostcardRepository;
import org.junit.*;
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
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestProcessConfiguration.class, PostcardManager.class})
@TestPropertySource(locations = "classpath:/config/process/application-unittest.properties")
public class PostcardManagerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    PostcardManager postcardManager;

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
        this.campaign.setValidFrom(LocalDate.of(2019, 1, 1));
        this.campaign.setValidTo(LocalDate.of(2022, 12, 31));
        this.campaign.setBrandText("Dies ist ein Beispiel-Grusstext");
        this.campaign.setBrandType(BrandingType.TEXT);
        campaignRepository.saveAndFlush(this.campaign);

    }

    @After
    public void tearDown() throws Exception {
        campaignRepository.delete(this.campaign);
    }

    // if you add client and id to application-unittest.properties, you can remove the "ignore"-annotation
    @Ignore
    @Test
    @Rollback
    public void createNewPostcard() throws Exception {


        PostcardDTO postcardDTO = new PostcardDTO(
                null, null, "test text", "Joe", "Smith", "Bernstrasse", "1a", "3000", "Bern",
                "Meier", "Hans", "Hauptstrasse", "2", "8000", "Zürich",
                LocalDateTime.now(), null, this.campaign.getId(), PostcardState.OPEN.name(), TransmissionState.OPEN.name()
        );
        Long id = postcardManager.createNewPostcard(postcardDTO, "123.1233.12323");
        // wait a little bit, so the async call should also be processed
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Postcard postcard = postcardRepository.getOne(id);
        Assert.assertEquals(TransmissionState.OK, postcard.getTransmissionState());
        Assert.assertNotNull(postcard.getKey());
    }


    // if you add client and id to application-unittest.properties, you can remove the "ignore"-annotation
    @Ignore
    @Test
    @Rollback
    public void createNewPostcardWithImage() throws Exception {

        URL url = this.getClass().getResource("/images/testbildPostkarte.png");

        BufferedImage originalImage = ImageIO.read(new File(url.toURI()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        SerialBlob sb = new SerialBlob(imageInByte);
        Image img = new Image();
        img.setFile(sb);
        imageRepository.saveAndFlush(img);


        PostcardDTO postcardDTO = new PostcardDTO(
                null, null, "test text", "Joe", "Smith", "Bernstrasse", "1a", "3000", "Bern",
                "Meier", "Hans", "Hauptstrasse", "2", "8000", "Zürich",
                LocalDateTime.now(), img.getId(), this.campaign.getId(), PostcardState.OPEN.name(), TransmissionState.OPEN.name()
        );
        Long id = postcardManager.createNewPostcard(postcardDTO, "123.1233.12323");
        // wait a little bit, so the async call should also be processed
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Postcard postcard = postcardRepository.getOne(id);
        Assert.assertEquals(TransmissionState.OK, postcard.getTransmissionState());
        Assert.assertNotNull(postcard.getKey());
    }
}