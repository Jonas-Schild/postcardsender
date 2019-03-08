package ch.schildj.postcardsender.process;

import ch.schildj.postcardsender.domain.model.Image;
import ch.schildj.postcardsender.domain.model.Postcard;
import ch.schildj.postcardsender.domain.repository.ImageRepository;
import ch.schildj.postcardsender.domain.repository.PostcardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class CleanUp
 * Delete unused content
 */

@Component
public class CleanUp {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PostcardRepository postcardRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CleanUp.class);


    /*
     * find unused images older than a day and delete them
     */
    @Transactional
    public void deleteUnusedImages() {

        LocalDateTime olderThan = LocalDateTime.now().minusDays(1);
        List<Image> images = imageRepository.getUnusedImages(olderThan);

        if (images != null && images.size() > 0) {
            images.forEach(image -> imageRepository.delete(image));
        }
    }

    /*
     * find unapproved(=not finished by the user) postcards older than a day and delete them
     */
    @Transactional
    public void deleteUnapprovedCards() {

        LocalDateTime olderThan = LocalDateTime.now().minusDays(1);
        List<Postcard> unapproved = postcardRepository.findUnapproved(olderThan);

        if (unapproved != null) {
            unapproved.forEach(postcard -> postcardRepository.delete(postcard));
        }

    }

    /*
     * Schedules Starting-Point
     */
    @Scheduled(cron = "${cron.cleanUpTrigger}")
    public void runCleanUp() {
        LOGGER.info("************RUN CLEANUP-JOB*****************");
        this.deleteUnapprovedCards();
        this.deleteUnusedImages();
    }

}
