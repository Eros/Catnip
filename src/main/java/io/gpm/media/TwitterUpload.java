package io.gpm.media;

import io.gpm.Catnip;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;

import java.io.File;
import java.io.InputStream;

/***
 * @author George
 * @since 02/01/2018
 */
public class TwitterUpload implements ImageUpload {

    private final Twitter twitter;

    public TwitterUpload(Configuration conf, OAuthAuthorization auth){
        twitter = new TwitterFactory(conf).getInstance(auth);
    }

    @Override
    public String upload(File image, String message) throws TwitterException {
        return twitter.updateStatus(new StatusUpdate(message).media(image)).getText();
    }

    @Override
    public String upload(File image) throws TwitterException {
        return twitter.updateStatus(new StatusUpdate("").media(image)).getText();
    }

    @Override
    public String upload(String fileName, InputStream imageBody) throws TwitterException {
        return twitter.updateStatus(new StatusUpdate("").media(fileName, imageBody)).getText();
    }

    @Override
    public String upload(String fileName, InputStream imageBody, String message) throws TwitterException {
        return twitter.updateStatus(new StatusUpdate(message).media(fileName, imageBody)).getText();
    }
}
