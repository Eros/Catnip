package io.gpm.media;

import twitter4j.TwitterException;

import java.io.File;
import java.io.InputStream;

/***
 * @author George
 * @since 02/01/2018
 */
public interface ImageUpload {

    String upload(File image, String message) throws TwitterException;
    String upload(File image) throws TwitterException;
    String upload(String fileName, InputStream imageBody) throws TwitterException;
    String upload(String fileName, InputStream imageBody, String message) throws TwitterException;
}
