package ch.schildj.postcardsender.apicall.process;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OAuth2 Token Provider
 */
class TokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);


    private final String TOKEN_URL;

    private final String CLIENT_ID;

    private final String CLIENT_SECRET;

    private final String SCOPE;

    /* constructor */
    public TokenProvider(String tokenUrl, String clientId, String clientSecret, String scope) {
        this.TOKEN_URL = tokenUrl;
        this.CLIENT_ID = clientId;
        this.CLIENT_SECRET = clientSecret;
        this.SCOPE = scope;
    }

    /* returns a new token */
    public String getNewToken() {
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(TOKEN_URL)
                    .setGrantType(GrantType.CLIENT_CREDENTIALS)
                    .setClientId(CLIENT_ID)
                    .setClientSecret(CLIENT_SECRET)
                    .setScope(SCOPE)
                    .buildQueryMessage();

            OAuthClient client = new OAuthClient(new URLConnectionClient());
            OAuthJSONAccessTokenResponse response = client.accessToken(request, OAuthJSONAccessTokenResponse.class);
            String token = response.getAccessToken();
            LOGGER.debug("Token from " + TOKEN_URL + " received: " + token);
            return token;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }


}




