package ch.schildj.postcardsender.apicall.process;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/*
 * Helper class to provide restTemplate
 */
class RestClientHelper {


    /*
     * Initialize basic restTemplate
     * @return RestTemplate
     */
    public static RestTemplate initializeBasicRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    /*
     * Initialize SSL restTemplate
     * @param  proxy-informations (null if not needed)
     * @return RestTemplate
     */
    public static RestTemplate getRestClientSSL(String truststoreLocation, String truststorePassword,
                                                String proxyLocation, Integer proxyPort,
                                                String proxyUsername, String proxyPassword) throws Exception {

        SSLContextBuilder sslContextBuilder = SSLContextBuilder.create();

        if (StringUtils.hasText(truststoreLocation)) {
            sslContextBuilder.loadTrustMaterial(RestClientHelper.class.getResource(truststoreLocation), truststorePassword.toCharArray());
        } else {
            sslContextBuilder.loadTrustMaterial((x509Certificates, s) -> true);
        }

        HttpClientBuilder clientBuilder = HttpClients.custom().setSSLContext(sslContextBuilder.build());


        if (StringUtils.hasText(proxyLocation)) {
            clientBuilder.setProxy(new HttpHost(proxyLocation, proxyPort == null ? 3128 : proxyPort));
        }

        if (StringUtils.hasText(proxyUsername)) {
            BasicCredentialsProvider credentials = new BasicCredentialsProvider();
            credentials.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxyUsername, proxyPassword));

            clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
                    .setDefaultAuthSchemeRegistry(name -> {
                        if ("basic".equalsIgnoreCase(name)) {
                            return new BasicSchemeFactory(Charset.forName("UTF8"));
                        }
                        return null;
                    })
                    .setDefaultCredentialsProvider(credentials);
        }


        HttpComponentsClientHttpRequestFactory clientHttpReq = new HttpComponentsClientHttpRequestFactory();
        clientHttpReq.setHttpClient(clientBuilder.build());

        RestTemplate template = new RestTemplate(clientHttpReq);
        template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return template;
    }


}
