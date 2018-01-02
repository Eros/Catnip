package io.gpm.media;

import twitter4j.HttpParameter;
import twitter4j.TwitterException;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;

/***
 * @author George
 * @since 02/01/2018
 */
public class MobypicUpload extends AbstractImageUploadImpl{

    MobypicUpload(Configuration config, String apiKey, OAuthAuthorization auth) {
        super(config, apiKey, auth);
    }

    @Override
    protected HttpParameter[] appendParameter(HttpParameter[] postParameter, HttpParameter[] appendParameter) {
        return new HttpParameter[0];
    }

    @Override
    protected void preUpload() throws TwitterException {
        uploadUrl = "https://api.mobypicture.com/2.0/upload.json";
        String verify = generateVerifyCredentialsAuthorizationHeader();

        headers.put("X-Auth-Service-Provider", TWITTER_VERIFY_CREDENTIALS_JSON);
        headers.put("X-Verify-Credentials-Authorization", verify);

        if(null == apiKey){
            throw new IllegalStateException("No API key has been found for Mobypic");
        }

        HttpParameter[] params = {
                new HttpParameter("key", apiKey),
                this.image};

        if(message != null){
            params = appandParameter(new HttpParameter[]{this.message}, params);
        }
        this.postParameter = params;
    }

    @Override
    protected String postUpload() throws TwitterException {
        return null;
    }
}
