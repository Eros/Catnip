package io.gpm;

import twitter4j.*;

import java.io.File;
import java.util.Arrays;

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
                mediaID[i-1] = media.getMediaId();
            }

            StatusUpdate update = new StatusUpdate(args[0]);
            update.setMediaIds(mediaID);
            System.out.println("Added status update with the media ID: " + Arrays.toString(mediaID));

            Status status = twitter.updateStatus(update);
            System.out.println("Successfully updated the status: " + status.getId() + " to: " + status.getText());
        } catch (TwitterException e) {
            System.out.println("Status update failed: " + e);
            e.printStackTrace();
        }
    }
}
