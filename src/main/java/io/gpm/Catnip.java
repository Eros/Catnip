package io.gpm;


import twitter4j.*;

import java.io.IOException;

/***
 * @author George
 * @since 21-Dec-17
 */
public class Catnip {


    public static void main(String... args) {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            User user = twitter.verifyCredentials();
            System.out.println("Verified credentials of user: " + user.getName());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
