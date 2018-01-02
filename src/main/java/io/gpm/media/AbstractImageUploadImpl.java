package io.gpm.media;

import lombok.Getter;
import twitter4j.*;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/***
 * @author George
 * @since 02/01/2018
 */
abstract class AbstractImageUploadImpl implements ImageUpload {

    @Getter
    static final String TWITTER_VERIFY_CREDENTIALS_JSON = "https://api.twitter.com/1.1/account/verify_credentials.json";

    private HttpClient client;
    private Configuration config = null;
    @Getter
    protected String apiKey = null;
    @Getter
    OAuthAuthorization auth = null;
    @Getter
    String uploadUrl = null;
    @Getter
    HttpParameter[] postParameter= null;
    private HttpParameter[] appendParameter = null;
    @Getter
    HttpParameter image = null;
    @Getter
    HttpParameter message = null;
    @Getter
    final Map<String, String> headers = new HashMap<String, String>();
    @Getter
    HttpResponse response = null;
    private static final Logger logger = Logger.getLogger(AbstractImageUploadImpl.class);

    AbstractImageUploadImpl(Configuration config, OAuthAuthorization auth){
        this.auth = auth;
        this.config = config;
        client = HttpClientFactory.getInstance(config.getHttpClientConfiguration());
    }

    AbstractImageUploadImpl(Configuration config, String apiKey, OAuthAuthorization auth){
        this(config, auth);
        this.apiKey = apiKey;
    }

    @Override
    public String upload(String fileName, InputStream imageBody) throws TwitterException {
        this.image = new HttpParameter("media", fileName, imageBody);
        return upload();
    }

    @Override
    public String upload(String fileName, InputStream imageBody, String message) throws TwitterException {
        this.image = new HttpParameter("media", fileName, imageBody);
        this.message = new HttpParameter("message", message);
        return upload();
    }

    @Override
    public String upload(File file, String message) throws TwitterException {
        this.image = new HttpParameter("media", file);
        this.message = new HttpParameter("message", message);
        return upload();
    }

    @Override
    public String upload(File file) throws TwitterException {
        this.image = new HttpParameter("media", file);
        return upload();
    }

    private String upload() throws TwitterException {
        if(config.getMediaProviderParameters() != null){
            Set set = config.getMediaProviderParameters().keySet();
            HttpParameter[] params = new HttpParameter[set.size()];
            int pos = 0;

            for(Object k : set) {
                String v = config.getMediaProviderParameters().getProperty((String) k);
                params[pos] = new HttpParameter((String) k, v);
                pos++;
            }
            this.appendParameter = params;
        }
        preUpload();
        if(this.postParameter == null){
            throw new AssertionError("Implementation was incomplete. Error due to post parameters");
        }
        if(this.uploadUrl == null) {
            throw new AssertionError("Implementation was incomplete. Error due to upload url");
        }
        if(config.getMediaProviderParameters() != null && this.appendParameter.length > 0){
            this.postParameter = appendParameter(this.postParameter, this.appendParameter);
        }
        Map<String, String> newHeaders = new HashMap<String, String>(client.getRequestHeaders());
        newHeaders.putAll(headers);
        response = client.request(new HttpRequest(RequestMethod.POST, uploadUrl, postParameter, null, headers), null);

        String mediaUrl = postUpload();

        logger.debug("Uploaded url: " + mediaUrl);

        return mediaUrl;
    }

    protected abstract HttpParameter[] appendParameter(HttpParameter[] postParameter, HttpParameter[] appendParameter);

    protected abstract void preUpload() throws TwitterException;

    protected abstract String postUpload() throws TwitterException;


    HttpParameter[] appandParameter(HttpParameter[] source, HttpParameter[] destination){
        int srcLength = source.length;
        int dstLength = destination.length;
        HttpParameter[] ret = new HttpParameter[srcLength + dstLength];
        System.arraycopy(source, 0, ret, 0, srcLength);
        System.arraycopy(destination, 0, ret, srcLength, dstLength);
        return ret;
    }

    String generateVerifyCredentialsAuthorizationHeader(){
        List<HttpParameter> oauthSignaturePerms = auth.generateOAuthSignatureHttpParams("GET",
                AbstractImageUploadImpl.TWITTER_VERIFY_CREDENTIALS_JSON);
        return "OAuth realm=\"https://api.twitter.com/\"," + OAuthAuthorization.encodeParameters(oauthSignaturePerms, ",", true);
    }

    protected String geneateVerifyCredentialsAuthorizationURL(String verifyCredentials){
        List<HttpParameter> oauthSignatureParams = auth.generateOAuthSignatureHttpParams("GET", verifyCredentials);
        return verifyCredentials + "?" + OAuthAuthorization.encodeParameters(oauthSignatureParams);
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;

        AbstractImageUploadImpl t = (AbstractImageUploadImpl) o;

        if(apiKey != null ? !apiKey.equals(t.apiKey) : t.apiKey != null)
            return false;
        if(!Arrays.equals(appendParameter, t.appendParameter))
            return false;
        if(client != null ? !client.equals(t.client) : t.client != null)
            return false;
        if(config != null ? !config.equals(t.config) : t.config != null)
            return false;
        if(headers != null ? !headers.equals(t.headers) : t.headers != null)
            return false;
        if(response != null ? !response.equals(t.response) : t.response != null)
            return false;
        if(image != null ? !image.equals(t.image) : t.image != null)
            return false;
        if(message != null ? !message.equals(t.message) : t.message != null)
            return false;
        if(auth != null ? !auth.equals(t.auth) : t.auth != null)
            return false;
        if(!Arrays.equals(postParameter, t.postParameter))
            return false;
        if(uploadUrl != null ? !uploadUrl.equals(t.uploadUrl) : t.uploadUrl != null)
            return false;

        return true;
    }

    @Override
    public int hashCode(){
        int result = client != null ? client.hashCode() : 0;
        result = 31 * result + (config != null ? config.hashCode() : 0);
        result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
        result = 31 * result + (auth != null ? auth.hashCode() : 0);
        result = 31 * result + (uploadUrl != null ? uploadUrl.hashCode() : 0);
        result = 31 * result + (postParameter != null ? Arrays.hashCode(postParameter) : 0);
        result = 31 * result + (appendParameter != null ? Arrays.hashCode(appendParameter) : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        result = 31 * result + (response != null ? response.hashCode() : 0);

        return result;
    }
}
