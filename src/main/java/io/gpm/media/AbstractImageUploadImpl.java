package io.gpm.media;

import twitter4j.*;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/***
 * @author George
 * @since 02/01/2018
 */
abstract class AbstractImageUploadImpl implements ImageUpload {

    static final String TWITTER_VERIFY_CREDENTIALS_JSON = "https://api.twitter.com/1.1/account/verify_credentials.json";

    private HttpClient client;
    private Configuration config = null;
    protected String apiKey = null;
    OAuthAuthorization auth = null;
    String uploadUrl = null;
    HttpParameter[] postParameter= null;
    private HttpParameter[] appendParameter = null;
    HttpParameter image = null;
    HttpParameter message = null;
    final Map<String, String> headers = new HashMap<String, String>();
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
}
