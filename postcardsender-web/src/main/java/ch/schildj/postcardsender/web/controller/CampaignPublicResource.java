/*
 * ------------------------------------------------------------------------------------------------
 * Copyright 2015 by Swiss Post, Information Technology Services
 * ------------------------------------------------------------------------------------------------
 * $Id: SampleCountryResource.java 14118 2015-06-19 14:22:34Z odermattr $
 * ------------------------------------------------------------------------------------------------
 */
package ch.schildj.postcardsender.web.controller;

import ch.schildj.postcardsender.domain.model.dto.CampaignDTO;
import ch.schildj.postcardsender.domain.repository.CampImageSetRepository;
import ch.schildj.postcardsender.domain.repository.CampaignRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("api/public/campaign")
@Api(value="campaign", description="Operations relevant on the campaign, open")
public class CampaignPublicResource {

    private final CampaignRepository campaignRepository;
    private final CampImageSetRepository campImageSetRepository;


    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignPublicResource.class);

    @Autowired
    public CampaignPublicResource(CampaignRepository campaignRepository, CampImageSetRepository campImageSetRepository) {
        this.campaignRepository = campaignRepository;
        this.campImageSetRepository = campImageSetRepository;
    }

    @ApiOperation(value = "View a list of available active campaigns")
    @GetMapping(value = "/currentActive", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CampaignDTO> getCurrentActivecampaign() throws Exception {
        LocalDate targetdate = LocalDate.now();
        return campaignRepository.findAllActive(targetdate);
    }

    @ApiOperation(value = "Select a campaigns")
    @GetMapping(value = "/key/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CampaignDTO getCampaignByKey(@PathVariable("key") String key) throws Exception {
        return campaignRepository.findFirstByKey(key);
    }

    @ApiOperation(value = "View a list of images defined for a campaign")
    @GetMapping(value = "/images/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Long> getImages(@PathVariable("id") Long id) throws Exception {
        return campImageSetRepository.findCampImageSetByCampaign(id);
    }


}
