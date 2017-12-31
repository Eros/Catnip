package io.gpm;


import twitter4j.*;

import java.io.*;
import java.util.Properties;

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

        File file = new File("addPathNameHere");
        Properties p = new Properties();
        InputStream in = null;
        OutputStream out = null;

        try {
            if(file.exists()) {
                in = new FileInputStream(file);
                p.load(in);
            }

            if(args.length < 2){
                if(null == p.getProperty("outh.consumerKey") && null == p.getProperty("outh.consumerSecret")){
                    System.out.println("Consumer key and consumer secret have not been set!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
