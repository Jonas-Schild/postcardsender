package ch.schildj.postcardsender.apicall.process;

import ch.schildj.postcardsender.apicall.model.PostcardApiDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
 * This class provides a new instance of the PostCardApiCall.class
 * to do the communication with the Postcard API
 */
@Component
public class PostcardApiCallProvider {

    @Value("${api.post.request.url}")
    private String URL;

    @Value("${api.post.requst.url.extend.create}")
    private String EXTEND_CREATE;

    @Value("${api.post.requst.url.extend.state}")
    private String EXTEND_STATE;

    @Value("${api.post.requst.url.extend.approve}")
    private String EXTEND_APPROVE;

    @Value("${api.post.requst.url.extend.sender}")
    private String EXTEND_SENDER;

    @Value("${api.post.requst.url.extend.recipient}")
    private String EXTEND_RECIPIENT;

    @Value("${api.post.requst.url.extend.image}")
    private String EXTEND_IMAGE;

    @Value("${api.post.requst.url.extend.sendertext}")
    private String EXTEND_TEXT;

    @Value("${api.post.requst.url.extend.branding.text}")
    private String EXTEND_BRANDINGTEXT;

    @Value("${api.post.requst.url.extend.branding.image}")
    private String EXTEND_BRANDINGIMAGE;

    @Value("${api.post.requst.url.extend.branding.stamp}")
    private String EXTEND_STAMP;

    @Value("${api.post.requst.url.extend.branding.qrtag}")
    private String EXTEND_BRANDINGQR;

    @Value("${api.post.requst.url.extend.preview.front}")
    private String EXTEND_PREV_FRONT;

    @Value("${api.post.requst.url.extend.preview.back}")
    private String EXTEND_PREV_BACK;

    @Value("${api.post.requst.url.statistic}")
    private String EXTEND_STATISTIC;

    @Value("${api.post.client.url.token}")
    private String TOKEN_URL;

    @Value("${api.post.client.id}")
    private String CLIENT_ID;

    @Value("${api.post.client.secret}")
    private String CLIENT_SECRET;

    @Value("${api.post.client.scope}")
    private String SCOPE;

    @Value("${api.proxyUser}")
    private String PROXY_USER;
    @Value("${api.proxyPassword}")
    private String PROXY_PASSWORD;
    @Value("${api.proxyUrl}")
    private String PROXY_URL;
    @Value("${api.proxyPort}")
    private Integer PROXY_PORT;


    /* new PostCardApiCall instance with postcard set */
    public PostCardApiCall getNewPostCardApiCall(PostcardApiDto postcard) {
        return new PostCardApiCall(postcard,
                new TokenProvider(this.TOKEN_URL, this.CLIENT_ID, this.CLIENT_SECRET, this.SCOPE),
                this.URL,
                this.EXTEND_CREATE,
                this.EXTEND_STATE,
                this.EXTEND_APPROVE,
                this.EXTEND_SENDER,
                this.EXTEND_RECIPIENT,
                this.EXTEND_IMAGE,
                this.EXTEND_TEXT,
                this.EXTEND_BRANDINGTEXT,
                this.EXTEND_BRANDINGIMAGE,
                this.EXTEND_STAMP,
                this.EXTEND_BRANDINGQR,
                this.EXTEND_PREV_FRONT,
                this.EXTEND_PREV_BACK,
                this.EXTEND_STATISTIC,
                this.PROXY_USER,
                this.PROXY_PASSWORD,
                this.PROXY_URL,
                this.PROXY_PORT);
    }

    /* new PostCardApiCall instance only with campaign set */
    public PostCardApiCall getNewPostCardApiCall(String campaign) {
        return new PostCardApiCall(campaign,
                new TokenProvider(this.TOKEN_URL, this.CLIENT_ID, this.CLIENT_SECRET, this.SCOPE),
                this.URL,
                this.EXTEND_CREATE,
                this.EXTEND_STATE,
                this.EXTEND_APPROVE,
                this.EXTEND_SENDER,
                this.EXTEND_RECIPIENT,
                this.EXTEND_IMAGE,
                this.EXTEND_TEXT,
                this.EXTEND_BRANDINGTEXT,
                this.EXTEND_BRANDINGIMAGE,
                this.EXTEND_STAMP,
                this.EXTEND_BRANDINGQR,
                this.EXTEND_PREV_FRONT,
                this.EXTEND_PREV_BACK,
                this.EXTEND_STATISTIC,
                this.PROXY_USER,
                this.PROXY_PASSWORD,
                this.PROXY_URL,
                this.PROXY_PORT);
    }
}
