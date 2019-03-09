
package ch.schildj.postcardsender.web.controller;

import ch.schildj.postcardsender.domain.model.dto.*;
import ch.schildj.postcardsender.domain.repository.CampaignRepository;
import ch.schildj.postcardsender.process.CampaignManager;
import ch.schildj.postcardsender.process.PostcardManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("api/protected/campaign")
@Api(value = "campaign", description = "Operations relevant on the campaign, only availabe when logged-in")
public class CampaignProtectedResource {

    private final CampaignRepository campaignRepository;

    private final PostcardManager postcardManager;

    private final CampaignManager campaignManager;


    private static final int MAX_PAGE_SIZE = 10;


    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignProtectedResource.class);

    @Autowired
    public CampaignProtectedResource(CampaignRepository campaignRepository, PostcardManager postcardManager,
                                     CampaignManager campaignManager) {
        this.campaignRepository = campaignRepository;
        this.postcardManager = postcardManager;
        this.campaignManager = campaignManager;
    }

    @ApiOperation(value = "View a list of existing campaigns")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CampaignDTO> getAllCampaign() throws Exception {
        return campaignRepository.findAllDTO();
    }

    @ApiOperation(value = "Search a specific set of created postcards")
    @PostMapping(value = "/searchPostcards/{campId}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DataSizeAndDataDTO<PostcardDTO> getAllPostcardsFromCampaign(@PathVariable("campId") Long campId, @PathVariable("page") int page, @RequestBody CardSearchDTO cardSearchDTO) {
        PageRequest pageRequest = PageRequest.of(page, MAX_PAGE_SIZE, Sort.by("mdate").descending());
        Page<PostcardDTO> pageResult = this.postcardManager.findAllByCampaign(campId, pageRequest, cardSearchDTO);
        List<PostcardDTO> postcardDTOS = pageResult.getContent();

        return new DataSizeAndDataDTO<>((int) pageResult.getTotalElements(), postcardDTOS);
    }

    @ApiOperation(value = "Export a specific set of created postcards as Excel-file")
    @RequestMapping(path = "/generateExport/{campId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getPostcardReport(@PathVariable("campId") Long campId, @RequestBody CardSearchDTO cardSearchDTO, HttpServletResponse response) {
        try {
            this.postcardManager.generateReportForCards(campId, cardSearchDTO, response.getOutputStream());
        } catch (Exception e) {
            LOGGER.error("error while generating postcard-report: ", e);
        }
    }


    @ApiOperation(value = "get statistics")
    @GetMapping(value = "/statistic/{campId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CampaignStatisticDTO> getStatistic(@PathVariable("campId") Long campId) {
        try {
            CampaignStatisticDTO csr = campaignManager.getStatistic(campId);
            return ResponseEntity.status(HttpStatus.OK).body(csr);
        } catch (Exception e) {
            LOGGER.error("FAIL to get Statistic !" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @ApiOperation(value = "Remove an image from a campaign")
    @DeleteMapping(value = "/removeImage/{campId}/{imgId}")
    @ResponseBody
    public void removeImage(@PathVariable("campId") Long campId, @PathVariable("imgId") Long imgId) {
        campaignManager.deleteImages(campId, imgId);
    }

    @ApiOperation(value = "Add an image to a campaign")
    @PutMapping(value = "/addImage/{campId}/{imgId}")
    @ResponseBody
    public void addImage(@PathVariable("campId") Long campId, @PathVariable("imgId") Long imgId) {
        campaignManager.addImage(campId, imgId);
    }

    @ApiOperation(value = "Remove stamp from campaign")
    @DeleteMapping(value = "/removeStamp/{campId}")
    @ResponseBody
    public void removeStamp(@PathVariable("campId") Long campId) {
        campaignManager.deleteStamp(campId);
    }

    @ApiOperation(value = "Add stamp to campaign")
    @PutMapping(value = "/addStamp/{campId}/{imgId}")
    @ResponseBody
    public void addStamp(@PathVariable("campId") Long campId, @PathVariable("imgId") Long imgId) {
        campaignManager.addStamp(campId, imgId);
    }

    @ApiOperation(value = "Save campaign")
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<Long> saveCampaign(@RequestBody CampaignDTO campaignDTO) {
        try {

            Long campId = campaignManager.saveCampaign(campaignDTO);
            return ResponseEntity.status(HttpStatus.OK).body(campId);
        } catch (Exception e) {
            LOGGER.error("FAIL to save Campaign !" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
