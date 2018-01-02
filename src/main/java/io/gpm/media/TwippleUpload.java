package io.gpm.media;

import twitter4j.HttpParameter;
import twitter4j.TwitterException;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;

/***
 * @author George
 * @since 02/01/2018
 */
public class TwippleUpload extends AbstractImageUploadImpl {
    TwippleUpload(Configuration config, OAuthAuthorization auth) {
        super(config, auth);
    }

    @Override
    protected HttpParameter[] appendParameter(HttpParameter[] postParameter, HttpParameter[] appendParameter) {
        return new HttpParameter[0];
    }

    @Override
    protected void preUpload() throws TwitterException {
        uploadUrl = "http://p.twipple.jp/api/upload";
        String signed = generateVerifyCredentialsAuthorizationHeader();

        this.postParameter = new HttpParameter[]{new HttpParameter("verify_url", signed), this.image};
    }

    @Override
    protected String postUpload() throws TwitterException {
        int status = response.getStatusCode();
        if(status != 200)
            throw new TwitterException("Twipple image upload return invalid status");

        String str = response.asString();
        if(str.contains("<rsp stat\"fail\">")){
            String er = str.substring(str.indexOf("msg") + 5, str.lastIndexOf("\""));
            throw new TwitterException("Twipple upload has failed with the error: " + er);
        }
        if(str.contains("<rsp stat=\"ok\">")){
            return str.substring(str.indexOf("<mediaurl>") + "<mediaurl>".length(), str.indexOf("</mediaurl>"));

        }
        return null;
    }
}
