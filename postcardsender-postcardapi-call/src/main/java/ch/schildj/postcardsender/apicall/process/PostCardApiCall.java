package ch.schildj.postcardsender.apicall.process;


import ch.schildj.postcardsender.apicall.model.PostcardApiDto;
import ch.schildj.postcardsender.apicall.model.RequestLogDto;
import ch.schildj.postcardsender.apicall.model.apiRequestDto.*;
import ch.schildj.postcardsender.apicall.model.apiResponseDto.CampaignStatisticResponse;
import ch.schildj.postcardsender.apicall.model.apiResponseDto.DefaultResponse;
import ch.schildj.postcardsender.apicall.model.apiResponseDto.PostcardStateResponse;
import ch.schildj.postcardsender.apicall.model.apiResponseDto.PreviewResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * This class does the communication with the Postcard API
 */

public class PostCardApiCall {


    private static final Logger LOGGER = LoggerFactory.getLogger(PostCardApiCall.class);


    private final String URL;
    private final String EXTEND_CREATE;
    private final String EXTEND_STATE;
    private final String EXTEND_APPROVE;
    private final String EXTEND_SENDER;
    private final String EXTEND_RECIPIENT;
    private final String EXTEND_IMAGE;
    private final String EXTEND_TEXT;
    private final String EXTEND_BRANDINGTEXT;
    private final String EXTEND_BRANDINGIMAGE;
    private final String EXTEND_STAMP;
    private final String EXTEND_BRANDINGQR;
    private final String EXTEND_PREV_FRONT;
    private final String EXTEND_PREV_BACK;
    private final String EXTEND_STATISTIC;
    private final String PROXY_USER;
    private final String PROXY_PASSWORD;
    private final String PROXY_URL;
    private final Integer PROXY_PORT;

    private final TokenProvider tokenProvider;

    private RequestLogDto requestLog;
    private ArrayList<RequestLogDto> requestLogDtos;
    private PostcardApiDto postcard;
    private final String campaignKey;


    public enum PostcardSide {
        FRONT, BACK
    }

    /*
     * constructor with postcard
     *
     * @param postcard      - the postcard to be transmitted
     * @param tokenProvider - provider for oauth2-token
     * @param url           - the url-address where the requests are send to
     * @param extend_[..]   - the url-extentions to do the specific request
     * @param proxy_[..]    - proxy-settings, if you are behind a proxy
     */
    public PostCardApiCall(PostcardApiDto postcard,
                           TokenProvider tokenProvider,
                           String url,
                           String extend_create,
                           String extend_state,
                           String extend_approve,
                           String extend_sender,
                           String extend_recipient,
                           String extend_image,
                           String extend_text,
                           String extend_brandingtext,
                           String extend_brandingimage,
                           String extend_stamp,
                           String extend_brandingqr,
                           String extend_prev_front,
                           String extend_prev_back,
                           String extend_statistic,
                           String proxy_user,
                           String proxy_password,
                           String proxy_url,
                           Integer proxy_port) {
        this(postcard.getCampKey(),
                tokenProvider,
                url,
                extend_create,
                extend_state,
                extend_approve,
                extend_sender,
                extend_recipient,
                extend_image,
                extend_text,
                extend_brandingtext,
                extend_brandingimage,
                extend_stamp,
                extend_brandingqr,
                extend_prev_front,
                extend_prev_back,
                extend_statistic,
                proxy_user,
                proxy_password,
                proxy_url,
                proxy_port);
        this.postcard = postcard;
        this.addNewRequestLog();

    }

    /*
     * constructor only with campaign
     *
     * @param campaign      - the key of the target campaign
     * @param tokenProvider - provider for oauth2-token
     * @param url           - the url-address where the requests are send to
     * @param extend_[..]   - the url-extentions to do the specific request
     * @param proxy_[..]    - proxy-settings, if you are behind a proxy
     */
    public PostCardApiCall(String campaign,
                           TokenProvider tokenProvider,
                           String url,
                           String extend_create,
                           String extend_state,
                           String extend_approve,
                           String extend_sender,
                           String extend_recipient,
                           String extend_image,
                           String extend_text,
                           String extend_brandingtext,
                           String extend_brandingimage,
                           String extend_stamp,
                           String extend_brandingqr,
                           String extend_prev_front,
                           String extend_prev_back,
                           String extend_statistic,
                           String proxy_user,
                           String proxy_password,
                           String proxy_url,
                           Integer proxy_port) {
        this.tokenProvider = tokenProvider;
        this.campaignKey = campaign;
        this.URL = url;
        this.EXTEND_CREATE = extend_create;
        this.EXTEND_STATE = extend_state;
        this.EXTEND_APPROVE = extend_approve;
        this.EXTEND_SENDER = extend_sender;
        this.EXTEND_RECIPIENT = extend_recipient;
        this.EXTEND_IMAGE = extend_image;
        this.EXTEND_TEXT = extend_text;
        this.EXTEND_BRANDINGTEXT = extend_brandingtext;
        this.EXTEND_BRANDINGIMAGE = extend_brandingimage;
        this.EXTEND_STAMP = extend_stamp;
        this.EXTEND_BRANDINGQR = extend_brandingqr;
        this.EXTEND_PREV_FRONT = extend_prev_front;
        this.EXTEND_PREV_BACK = extend_prev_back;
        this.EXTEND_STATISTIC = extend_statistic;
        this.PROXY_USER = proxy_user;
        this.PROXY_PASSWORD = proxy_password;
        this.PROXY_URL = proxy_url;
        this.PROXY_PORT = proxy_port;

    }

    /* creates a new Log-Object */
    private void addNewRequestLog() {
        this.requestLog = new RequestLogDto();
        this.requestLog.setPostcardId(this.postcard.getId());
        this.requestLog.setPostcardKey(this.postcard.getKey());
        if (this.requestLogDtos == null) this.requestLogDtos = new ArrayList<>();
        this.requestLogDtos.add(this.requestLog);
    }


    /*
     * Creates a new postcard in the given campaign.
     * @return the log of the communication
     * */
    public ArrayList<RequestLogDto> createCard() throws Exception {

        String url = this.prepareUrl(this.EXTEND_CREATE);

        PostcardRequestDto body = new PostcardRequestDto(this.postcard);

        DefaultResponse response = this.callWithDefaultResponse(HttpMethod.POST,
                url,
                this.convertDtoToJsonString(body));

        if (response != null) {

            this.readDefaultResponse(response);

            /* send the images in separate calls */
            if (this.postcard.getFrontImage() != null) {
                LOGGER.info("APICALL: Create Image " + this.postcard.getKey());
                this.addNewRequestLog();
                this.updateFrontImage();
            }
            if (this.postcard.getStampImage() != null) {
                this.addNewRequestLog();
                this.updateStamp();
            }
            if (this.postcard.getBrandImage() != null) {
                this.addNewRequestLog();
                this.updateBrandingImage();
            }
        }

        return this.requestLogDtos;


    }

    /* Gets the actual state for the given postcard.
     * @return the state of the postcard
     * */
    public String getState() {

        String url = this.prepareUrl(this.EXTEND_STATE);

        return this.callWithStateResponse(HttpMethod.GET, url);

    }

    /*
     * Approve the given postcard for printing.
     *  @return the log of the communication
     */
    public ArrayList<RequestLogDto> approve() throws Exception {

        DefaultResponse response = this.callWithDefaultResponse(HttpMethod.POST,
                this.prepareUrl(this.EXTEND_APPROVE),
                "");

        this.readDefaultResponse(response);


        return this.requestLogDtos;

    }

    /*
     * Updates the sender address in the given postcard.
     * @return the log of the communication
     */
    public ArrayList<RequestLogDto> updateSender() throws Exception {

        SenderAddressDto body = new SenderAddressDto(this.postcard);

        DefaultResponse response = this.callWithDefaultResponse(HttpMethod.PUT,
                this.prepareUrl(this.EXTEND_SENDER),
                this.convertDtoToJsonString(body));

        this.readDefaultResponse(response);

        return this.requestLogDtos;

    }

    /*
     * Updates the recipient address in the given postcard.
     * @return the log of the communication
     */
    public ArrayList<RequestLogDto> updateRecipient() throws Exception {

        RecipientAddressDto body = new RecipientAddressDto(this.postcard);

        DefaultResponse response = this.callWithDefaultResponse(HttpMethod.PUT,
                this.prepareUrl(this.EXTEND_RECIPIENT),
                this.convertDtoToJsonString(body));

        this.readDefaultResponse(response);
        return this.requestLogDtos;

    }

    /*
     * Updates the sender text in the given postcard.
     * @return the log of the communication
     */
    public ArrayList<RequestLogDto> updateText() throws Exception {

        DefaultResponse response = this.callWithDefaultResponse(HttpMethod.PUT,
                this.prepareUrl(this.EXTEND_TEXT),
                this.postcard.getText());

        this.readDefaultResponse(response);
        return this.requestLogDtos;

    }

    /* Updates the branding text in the given postcard.
     * If branding text (body) is empty, branding text will be reset on postcard.
     * @return the log of the communication
     */
    public ArrayList<RequestLogDto> updateBrandingText() throws Exception {
        BrandingTextDto body = new BrandingTextDto(this.postcard);

        DefaultResponse response = this.callWithDefaultResponse(HttpMethod.PUT,
                this.prepareUrl(this.EXTEND_BRANDINGTEXT),
                this.convertDtoToJsonString(body));

        this.readDefaultResponse(response);
        return this.requestLogDtos;
    }

    /*
     * Updates the front image in the given postcard.
     * @return the log of the communication
     * */
    public ArrayList<RequestLogDto> updateFrontImage() throws Exception {


        DefaultResponse response = this.putImageWithDefaultResponse(this.prepareUrl(this.EXTEND_IMAGE),
                this.postcard.getFrontImage(), "image");

        if (response != null) {
            this.readDefaultResponse(response);
        }


        return this.requestLogDtos;


    }


    /*
     * Updates the branding image in the given postcard.
     * @return the log of the communication
     */
    public ArrayList<RequestLogDto> updateBrandingImage() throws Exception {
        DefaultResponse response = this.putImageWithDefaultResponse(this.prepareUrl(this.EXTEND_BRANDINGIMAGE),
                this.postcard.getBrandImage(), "image");
        if (response != null) {
            this.readDefaultResponse(response);
        }
        return this.requestLogDtos;
    }

    /*
     * Uploads a logo as stamp in the given postcard.
     * @return the log of the communication
     */
    private ArrayList<RequestLogDto> updateStamp() throws Exception {
        DefaultResponse response = this.putImageWithDefaultResponse(this.prepareUrl(this.EXTEND_STAMP),
                this.postcard.getStampImage(), "stamp");
        if (response != null) {
            this.readDefaultResponse(response);
        }
        return this.requestLogDtos;
    }

    /*
     * Updates the branding QR tag information in the given postcard.
     * If branding qr tag (body) is empty, branding qr tag will be reset on postcard.
     * @return the log of the communication
     * */
    public ArrayList<RequestLogDto> updateBrandingQr() throws Exception {
        BrandingQRCodeDto body = new BrandingQRCodeDto(this.postcard);

        DefaultResponse response = this.callWithDefaultResponse(HttpMethod.PUT,
                this.prepareUrl(this.EXTEND_BRANDINGQR),
                this.convertDtoToJsonString(body));

        this.readDefaultResponse(response);
        return this.requestLogDtos;

    }

    /*
     * Gives a preview of the front or back side for the given postcard
     * @return the preview as base64-encoded String
     */
    public String getPreview(PostcardSide side) throws Exception {

        String requestUrl;
        if (side.equals(PostcardSide.FRONT)) {
            requestUrl = this.prepareUrl(this.EXTEND_PREV_FRONT);
        } else {
            requestUrl = this.prepareUrl(this.EXTEND_PREV_BACK);
        }

        PreviewResponse pr = this.callWithPreviewResponse(HttpMethod.GET, requestUrl);

        return pr.getImagedata();

    }

    /*
     * Gets the actual statistic of the given campaign
     * @return the statistic for a campaign
     */
    public CampaignStatisticResponse getCampaignStatistic() throws Exception {
        return this.callWithStatisticResponse(HttpMethod.GET, this.prepareUrl(this.EXTEND_STATISTIC));
    }

    /*
     * converts an Object to a JSON
     *
     * @param  data    - Object to be converted
     * @return the JSON-string
     */
    private String convertDtoToJsonString(Object data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(data);
    }

    /*
     * concat url and extention and replace placeholder
     * @param  urlExtention    - Extetion to be added to the base-url
     * @return the callable url
     */
    private String prepareUrl(String urlExtention) {
        String url = URL + urlExtention;
        if (this.postcard != null && this.postcard.getKey() != null) {
            url = url.replace("{cardKey}", this.postcard.getKey());
        }
        url = url.replace("{campaignKey}", this.campaignKey);
        return url;
    }

    /*
     * Read the Response and saves the interesting parts
     * @param  response    - Element to be saved
     * */
    private void readDefaultResponse(DefaultResponse response) {
        if (this.postcard.getKey() == null || this.postcard.getKey().compareTo(response.getCardKey()) != 0) {
            this.postcard.setKey(response.getCardKey());
            this.requestLog.setPostcardKey(response.getCardKey());
        }
        this.requestLog.setErrors(response.getErrors());
        this.requestLog.setWarnings(response.getWarnings());

    }

    /*
     * do the HTTP-Call as default
     *
     * @param  httpMethod    - HTTP-Method to be called
     * @param  url           - HTTP-Method to be called
     * @param  data          - Data to transmit
     * @return the answer from the API
     */
    private DefaultResponse callWithDefaultResponse(HttpMethod httpMethod, String url, String data) {
        // log the Request
        String logReq = httpMethod.name() + " " + url + " " + data;
        if (logReq.length() > 2000) {
            logReq = logReq.substring(0, 2000);
        }
        requestLog.setRequest(logReq);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setCacheControl("no-cache");
            String currentToken = this.tokenProvider.getNewToken();
            headers.set("Authorization", "Bearer " + currentToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            RestTemplate restTemplate = RestClientHelper.getRestClientSSL(null, null,
                    PROXY_URL, PROXY_PORT, PROXY_USER, PROXY_PASSWORD);


            HttpEntity<String> httpEntity = new HttpEntity<String>(data, headers);


            ResponseEntity<DefaultResponse> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, DefaultResponse.class);

            this.requestLog.setRespHttpCode(responseEntity.getStatusCode().value());

            this.requestLog.setRespHttpError(responseEntity.getStatusCode().getReasonPhrase());


            return responseEntity.getBody();

        } catch (HttpStatusCodeException e) {
            this.requestLog.setRespHttpCode(e.getStatusCode().value());
            this.requestLog.setRespHttpError("Failed with " + e.getStatusCode().getReasonPhrase());
            return null;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            this.requestLog.setRespHttpError("Systemerror");
            return null;
        }
    }

    /*
     * do the HTTP-Call with images
     *
     * @param  url           - HTTP-Method to be called
     * @param  image         - Bytes to transmit
     * @param  key           - image or stamp
     * @return the answer from the API
     */
    private DefaultResponse putImageWithDefaultResponse(String url, byte[] image, String key) {
        try {
            // log the Request
            requestLog.setRequest("PUT " + url + " (" + key + ")");

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setCacheControl("no-cache");
            String currentToken = this.tokenProvider.getNewToken();
            headers.set("Authorization", "Bearer " + currentToken);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            RestTemplate restTemplate = RestClientHelper.getRestClientSSL(null, null,
                    PROXY_URL, PROXY_PORT, PROXY_USER, PROXY_PASSWORD);


            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();


            final HttpHeaders fileHeader = new HttpHeaders();

            fileHeader.setContentType(MediaType.IMAGE_PNG);

            fileHeader.add(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\""+key+"\"; filename=\"image.png\"");
            map.add(key , new HttpEntity<>(image, fileHeader));


            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);


            ResponseEntity<DefaultResponse> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, DefaultResponse.class);

            return responseEntity.getBody();

        } catch (HttpStatusCodeException e) {
            this.requestLog.setRespHttpCode(e.getStatusCode().value());
            this.requestLog.setRespHttpError(e.getStatusCode().getReasonPhrase());
            LOGGER.error("API call failed, Server respond with HTTP-Code" + e.getStatusCode().value());
            return null;
        } catch (Exception e) {
            LOGGER.error("Image upload failed");
            LOGGER.error(e.toString());
            this.requestLog.setRespHttpError("Systemerror");
            return null;
        }
    }


    /* do a HTTP-Call to get the state of a postcard
     *
     * @param  httpMethod    - HTTP-Method to be called
     * @param  url           - HTTP-Method to be called
     * @return the state of the postcard
     */
    private String callWithStateResponse(HttpMethod httpMethod, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setCacheControl("no-cache");
        String currentToken = this.tokenProvider.getNewToken();
        headers.set("Authorization", "Bearer " + currentToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate;
        try {
            restTemplate = RestClientHelper.getRestClientSSL(null, null,
                    PROXY_URL, PROXY_PORT, PROXY_USER, PROXY_PASSWORD);


            HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

            try {
                ResponseEntity<PostcardStateResponse> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, PostcardStateResponse.class);

                return responseEntity.getBody().getState().getState();

            } catch (HttpStatusCodeException e) {
                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                    return "NOT_FOUND";
                } else {
                    LOGGER.error(e.toString());
                    return "UNKNOWN";
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return "UNKNOWN";
        }
    }

    /*
     * do a HTTP-Call to get the state of a postcard
     *
     * @param  httpMethod    - HTTP-Method to be called
     * @param  url           - HTTP-Method to be called
     * @return the answer from the API
     */
    private PreviewResponse callWithPreviewResponse(HttpMethod httpMethod, String url) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setCacheControl("no-cache");
        String currentToken = this.tokenProvider.getNewToken();
        headers.set("Authorization", "Bearer " + currentToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = RestClientHelper.getRestClientSSL(null, null,
                PROXY_URL, PROXY_PORT, PROXY_USER, PROXY_PASSWORD);

        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<PreviewResponse> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, PreviewResponse.class);

        return responseEntity.getBody();

    }

    /*
     * do a HTTP-Call to get the statistics of a campaign
     *
     * @param  httpMethod    - HTTP-Method to be called
     * @param  url           - HTTP-Method to be called
     * @return the answer from the API
     */
    private CampaignStatisticResponse callWithStatisticResponse(HttpMethod httpMethod, String url) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setCacheControl("no-cache");
        String currentToken = this.tokenProvider.getNewToken();
        headers.set("Authorization", "Bearer " + currentToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = RestClientHelper.getRestClientSSL(null, null,
                PROXY_URL, PROXY_PORT, PROXY_USER, PROXY_PASSWORD);

        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<CampaignStatisticResponse> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, CampaignStatisticResponse.class);

        return responseEntity.getBody();
    }


}
