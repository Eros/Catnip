package io.gpm.media;

import twitter4j.auth.Authorization;
import twitter4j.auth.AuthorizationFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

/***
 * @author George
 * @since 02/01/2018
 */
public class ImageUploadFactory {

    private final Configuration configuration;
    private MediaProvider mediaProvider;
    private final String apiKey;

    public ImageUploadFactory(){
        this(ConfigurationContext.getInstance());
    }

    public ImageUploadFactory(Configuration config){
        String mediaProvider = config.getMediaProvider().toLowerCase();
        if("twitter".equals(mediaProvider)){
            mediaProvider = String.valueOf(MediaProvider.valueOf("TWITTER"));
        } else if("imgly".equals(mediaProvider) || "img_ly".equals(mediaProvider)){
            mediaProvider = String.valueOf(MediaProvider.valueOf("IMG_LY"));
        } else if("twipple".equals(mediaProvider)){
            mediaProvider = String.valueOf(MediaProvider.valueOf("TWIPPLE"));
        } else if("mobypicture".equals(mediaProvider)) {
            mediaProvider = String.valueOf(MediaProvider.valueOf("MOBYPICTURE"));
        } else {
            throw new IllegalArgumentException("Unsupported media!!");
        }
        this.configuration = config;
        apiKey = config.getMediaProviderAPIKey();
    }

    public ImageUpload getInstance(){
        return getInstance(mediaProvider);
    }

    public ImageUpload getInstance(Authorization authorization){
        return getInstance(mediaProvider, authorization);
    }

    public ImageUpload getInstance(MediaProvider mediaProvider){
        Authorization auth = AuthorizationFactory.getInstance(configuration);
        return getInstance(mediaProvider, auth);
    }

    /**
     * Returns an ImageUpload instance associated with the specified media provider
     *
     * @param mediaProvider media provider
     * @param authorization authorization
     * @return ImageUpload
     * @since Twitter4J 2.1.11
     */

    public ImageUpload getInstance(MediaProvider mediaProvider, Authorization authorization){
        if(!(authorization instanceof OAuthAuthorization)){
            throw new IllegalArgumentException("OAuth is required!");
        }

        OAuthAuthorization oauth = (OAuthAuthorization) authorization;

        if(mediaProvider == MediaProvider.TWITTER){
            //todo twitter upload
        } else if(mediaProvider == MediaProvider.IMG_LY){
            //todo imgly upload
        } else if(mediaProvider == MediaProvider.TWIPPLE){
            //todo twipple upload
        } else if(mediaProvider == MediaProvider.MOBYPICTURE){
            //todo mobypicture upload
        } else {
            throw new AssertionError("Unknown service provider!");
        }
        return (ImageUpload) new AssertionError("");
    }
}
