package io.gpm;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/***
 * @author George
 * @since 31-Dec-17
 */
public class SendTweet {

    public static void go(String[] args){
        if(args.length < 1){
            System.out.println("Incorrect usage for tweet!");
        }
         try {
             Twitter twitter = Catnip.twitter;
             Status status = twitter.updateStatus(args[0]);
             System.out.println("Updated status to: " + status.getText());
         } catch (TwitterException e) {
             e.printStackTrace();
         }
    }
}
