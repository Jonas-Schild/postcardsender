/*
 * ------------------------------------------------------------------------------------------------
 * Copyright 2015 by Swiss Post, Information Technology Services
 * ------------------------------------------------------------------------------------------------
 * $Id: SampleCountryResource.java 14118 2015-06-19 14:22:34Z odermattr $
 * ------------------------------------------------------------------------------------------------
 */
package ch.schildj.postcardsender.web.controller;

import ch.schildj.postcardsender.domain.model.dto.AddressDTO;
import ch.schildj.postcardsender.domain.model.dto.CardhistoryDTO;
import ch.schildj.postcardsender.domain.model.dto.PostcardDTO;
import ch.schildj.postcardsender.domain.repository.CardhistoryRepository;
import ch.schildj.postcardsender.domain.repository.ImageRepository;
import ch.schildj.postcardsender.exception.MaxLimitReachedException;
import ch.schildj.postcardsender.process.PostcardManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("api/public/postcard")
@Api(value = "postcard", description = "Operations relevant on the postcard")
public class PostcardPublicResource {

    private final ImageRepository imageRepository;

    private final CardhistoryRepository cardhistoryRepository;

    private final PostcardManager postcardManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostcardPublicResource.class);

    private class textDTO {
        private String text;

        public textDTO(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @Autowired
    public PostcardPublicResource(ImageRepository imageRepository, PostcardManager postcardManager, CardhistoryRepository cardhistoryRepository) {
        this.imageRepository = imageRepository;
        this.postcardManager = postcardManager;
        this.cardhistoryRepository = cardhistoryRepository;
    }

    @ApiOperation(value = "Approve postcard (finish editing)")
    @PutMapping(value = "/approve/{id}")
    @ResponseBody
    public void approvePostcard(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        postcardManager.approve(id);
    }

    @ApiOperation(value = "Create a new postcard")
    @PostMapping("/new")
    @ResponseBody
    public ResponseEntity<Long> createNewPostcard(@RequestBody PostcardDTO postcardDTO, HttpServletRequest request) {
        try {

            Long postcardId = postcardManager.createNewPostcard(postcardDTO, getCallerIpAddress(request));
            return ResponseEntity.status(HttpStatus.OK).body(postcardId);
        } catch (MaxLimitReachedException me) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
        } catch (Exception e) {
            LOGGER.error("FAIL to create card !" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @ApiOperation(value = "Update senderaddress")
    @PutMapping("/updateSender/{id}")
    @ResponseBody
    public void updateSender(@PathVariable("id") Long id, @RequestBody AddressDTO addressDTO) {
        postcardManager.updateSender(id, addressDTO);
    }

    @ApiOperation(value = "Update recipientaddress")
    @PutMapping("/updateRecipient/{id}")
    @ResponseBody
    public void updateRecipient(@PathVariable("id") Long id, @RequestBody AddressDTO addressDTO) {
        postcardManager.updateRecipient(id, addressDTO);
    }

    @ApiOperation(value = "Update sendertext")
    @PutMapping(value = "/updateText/{id}", consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public void updateText(@PathVariable("id") Long id, @RequestBody String text) {
        postcardManager.updateText(id, text);
    }

    @ApiOperation(value = "Update reference to frontimage")
    @PutMapping("/updateFrontImage/{id}")
    @ResponseBody
    public void updateFrontImage(@PathVariable("id") Long id, @RequestBody Long imageId) {
        postcardManager.updateFrontimage(id, imageId);
    }

    @ApiOperation(value = "show requests")
    @GetMapping(value = "/getHistory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CardhistoryDTO> getPostcardHistory(@PathVariable("id") Long id) {
        return cardhistoryRepository.findAllByPostcard(id);
    }

    @ApiOperation(value = "Get preview of a postcard-side")
    @GetMapping(value = "/preview/{id}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getPreview(@PathVariable("id") Long id, @PathVariable("type") String type) {
        try {
            String base64 = postcardManager.getPreview(id, type);
            return ResponseEntity.status(HttpStatus.OK).body(base64);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vorschau leer");
        }
    }


    /* get IP address of the sender */
    private static String getCallerIpAddress(HttpServletRequest request) {

        String ip = "";

        if (request != null) {
            // first check X-FORWARDED-FOR, if you are behind a proxy
            ip = request.getHeader("X-FORWARDED-FOR");
            if (ip == null || "".equals(ip)) {
                ip = request.getRemoteAddr();
            }
        }

        return ip;
    }


}
