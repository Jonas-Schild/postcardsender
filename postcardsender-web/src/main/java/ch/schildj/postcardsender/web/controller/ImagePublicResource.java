/*
 * ------------------------------------------------------------------------------------------------
 * Copyright 2015 by Swiss Post, Information Technology Services
 * ------------------------------------------------------------------------------------------------
 * $Id: SampleCountryResource.java 14118 2015-06-19 14:22:34Z odermattr $
 * ------------------------------------------------------------------------------------------------
 */
package ch.schildj.postcardsender.web.controller;

import ch.schildj.postcardsender.domain.model.Image;
import ch.schildj.postcardsender.domain.repository.ImageRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;


@RestController
@RequestMapping("api/public/image")
@Api(value="image", description="Operations with images")
public class ImagePublicResource {

    private final ImageRepository imageRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImagePublicResource.class);

    @Autowired
    public ImagePublicResource(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    @ApiOperation(value = "Get an image")
    @GetMapping(value = "/{id}", produces = MediaType.ALL_VALUE)
    @ResponseBody
    public void getImageById(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        try {
            Image img = imageRepository.findById(id).get();
            Blob blob = img.getFile();
            response.setContentType("image/jpeg, image/jpg, image/png");
            response.getOutputStream().write(blob.getBytes(1, (int) blob.length()));
            response.getOutputStream().close();
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw e;
        }
    }

    @ApiOperation(value = "upload image")
    @PostMapping("/post")
    @Transactional
    public ResponseEntity<Long> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {

            Image img = new Image();

            byte[] byteObjects = new byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            SerialBlob sb = new SerialBlob(byteObjects);

            img.setFile(sb);

            imageRepository.saveAndFlush(img);

            return ResponseEntity.status(HttpStatus.OK).body(img.getId());
        } catch (Exception e) {
            LOGGER.error("FAIL to upload " + file.getOriginalFilename() + "!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }




}
