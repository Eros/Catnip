package io.gpm;


import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
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
            } else {
                p.setProperty("oauth.consumerKey", args[0]);
                p.setProperty("oauth.consumerSecret", args[1]);
                out = new FileOutputStream("twitter4j.properties");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                    System.out.println("Input stream has been closed!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                    System.out.print("Output stream has been closed!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            RequestToken token = twitter.getOAuthRequestToken();
            System.out.println("Request token has been verified");
            System.out.println("Request token is: " + token.getToken());
            System.out.println("Request secret is: " + token.getTokenSecret());

            AccessToken access = null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while(access == null){
                System.out.println("Use the following url to grant access: " + token.getAuthorizationURL());

                try {
                    //noinspection Since15
                    Desktop.getDesktop().browse(new URI(token.getAuthorizationURL()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Enter any valid pins: ");
                String pin = reader.readLine();

                try {
                    if(pin.length() > 0){
                        access = twitter.getOAuthAccessToken(token, pin);
                    }
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
