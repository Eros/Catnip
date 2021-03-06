package io.gpm;


import io.gpm.media.TwitterUpload;
import lombok.Getter;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;

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

    @Getter
    static Twitter twitter = new TwitterFactory().getInstance();

    public static void main(String... args) {
        User user = null;
        Configuration config = null;
        try {
            user = twitter.verifyCredentials();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        System.out.println("Verified credentials of user: " + user.getName());

        File file = new File("addPathNameHere");
        Properties p = new Properties();
        InputStream in = null;
        OutputStream out = null;

        try {
            if (file.exists()) {
                in = new FileInputStream(file);
                p.load(in);
            }

            if (args.length < 2) {
                if (null == p.getProperty("oauth.consumerKey") && null == p.getProperty("outh.consumerSecret")) {
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
            if (in != null) {
                try {
                    in.close();
                    System.out.println("Input stream has been closed!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
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

            while (access == null) {
                System.out.println("Use the following url to grant access: " + token.getAuthorizationURL());

                try {
                    //noinspection Since15
                    Desktop.getDesktop().browse(new URI(token.getAuthorizationURL()));
                } catch (URISyntaxException | IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Enter any valid pins: ");
                String pin = reader.readLine();

                try {
                    if (pin.length() > 0) {
                        access = twitter.getOAuthAccessToken(token, pin);
                    } else {
                        access = twitter.getOAuthAccessToken(token);
                    }
                } catch (TwitterException e) {
                    if (401 == e.getStatusCode()) {
                        System.out.println("Was unable to find the access token!");
                    } else {
                        e.printStackTrace();
                    }
                }
            }
            try {
                p.setProperty("oauth.accessToken", access.getToken());
                p.setProperty("oauth.accessTokenSecret", access.getTokenSecret());
                out = new FileOutputStream(file);
                p.store(out, "twitter4j.properties");
                out.close();
            } catch (Exception i) {
                i.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                        System.out.println("Output stream has been closed!");
                    } catch (IOException i) {
                        i.printStackTrace();
                    }
                }
            }
        } catch (TwitterException | IOException e) {
            e.printStackTrace();
        }

        if(config == null) {
            config = twitter.getConfiguration();

        }
    }

    private void handle(){
        twitter.onRateLimitReached(e -> System.out.println("Rate limit remaining: " + e.getRateLimitStatus().getRemaining()
         + "/" + e.getRateLimitStatus().getLimit()));
    }
}
