package io.gpm;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.UploadedMedia;

import java.io.File;

/***
 * @author George
 * @since 31-Dec-17
 */
public class SendTweetedImage {

    public static void go(String... args){
        if(args.length < 1){
            System.out.println("Error sending multi media tweet!");
        }
        try {
            Twitter twitter = Catnip.twitter;

            long[] mediaID = new long[args.length - 1];
            for(int i = 1; i < args.length; i++) {
                System.out.println("Uploading image: " + i + "/" + (args.length - 1) + "] [" + args[i]  + "]");
                UploadedMedia media = twitter.uploadMedia(new File(args[i]));
                System.out.println("Uploaded image!\n " +
                        "Image information: \n" +
                        "ID:" + media.getMediaId()
                 + "Width: " + media.getImageWidth()
                + "Height: " + media.getImageHeight()
                + "Size: " + media.getSize()
                + "Format: " + media.getSize());

            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
