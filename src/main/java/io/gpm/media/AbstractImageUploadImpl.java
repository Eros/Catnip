package io.gpm.media;

import twitter4j.HttpClient;
import twitter4j.HttpParameter;
import twitter4j.HttpResponse;
import twitter4j.Logger;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;

import java.util.HashMap;
import java.util.Map;

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
}
