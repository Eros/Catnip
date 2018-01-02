package io.gpm.media;

import twitter4j.HttpParameter;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.TwitterException;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;

/***
 * @author George
 * @since 02/01/2018
 */
public class ImglyUpload extends AbstractImageUploadImpl {
    ImglyUpload(Configuration config, OAuthAuthorization auth) {
        super(config, auth);
    }

    @Override
    protected HttpParameter[] appendParameter(HttpParameter[] postParameter, HttpParameter[] appendParameter) {
        return new HttpParameter[0];
    }

    @Override
    protected void preUpload() throws TwitterException {
        uploadUrl = "http://img.ly/api/2/upload.json";
        String verifyCredentialsAuthorizationHeader = generateVerifyCredentialsAuthorizationHeader();
        headers.put("X-Auth-Service-Provider", TWITTER_VERIFY_CREDENTIALS_JSON);
        headers.put("X-Verify-Credentials-Authorization", verifyCredentialsAuthorizationHeader);

        HttpParameter[] params = {this.image};

        if(message != null){
            params = appendParameter(new HttpParameter[]{this.message}, params);
        }
        this.postParameter = params;
    }

    @Override
    protected String postUpload() throws TwitterException {
        int status = response.getStatusCode();
        if(status != 200)
            throw new TwitterException("ImgLy uploaded returned invalid status");

        String str = response.asString();

        try {
            JSONObject json = new JSONObject(response);
            if(!json.isNull("url"))
                return json.getString("url");
        } catch (JSONException e) {
            throw new TwitterException("Invalid ImgLy response: ( " + response + " ) " + " [ " + e + " ] ");
        }
        return null;
    }
}
