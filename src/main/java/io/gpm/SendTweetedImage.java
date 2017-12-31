package io.gpm;

import twitter4j.Twitter;

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
        }
    }
}
