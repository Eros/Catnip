package io.gpm;

import twitter4j.Twitter;

/***
 * @author George
 * @since 02/01/2018
 */
public class AsyncUpdater {

    private static final Object LOCK = new Object();

    public static void handle(String... args) throws InterruptedException {
        if(args.length < 1) {
            System.out.println("Incorrect usage! Aborting...");
        }
        Twitter twitter = Catnip.twitter;
    }
}
