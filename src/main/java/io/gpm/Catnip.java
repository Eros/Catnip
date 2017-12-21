package io.gpm;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/***
 * @author George
 * @since 21-Dec-17
 */
public class Catnip {

    //general bollocks for twitter
    @Getter
    public static final int TPS_STREAM_LENGTH = 1000;
    @Getter
    public static ClientBuilder builder = null;
    @Getter
    public static Client client = null;


    public static void main(String... args){
        //queing for the shit we send
        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>(TPS_STREAM_LENGTH);
        BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(TPS_STREAM_LENGTH);

        //hosts and crap
        Hosts host = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();

        //adds the terms (may not be needed but fuck it)
        List<String> terms = Lists.newArrayList("twitter", "cats", "cute cats");

        //authentication
        Authentication auth = new OAuth1(io.gpm.Constants.C_CONSUMER_KEY,
                io.gpm.Constants.C_CONSUMER_SECRET, io.gpm.Constants.C_TOKEN, io.gpm.Constants.C_SECRET);

        //client handling
        if(builder == null) {
            builder = new ClientBuilder()
                    .name("catnip-twitter-system")
                    .hosts(host)
                    .authentication(auth)
                    .endpoint(endpoint)
                    .processor(new StringDelimitedProcessor(messageQueue))
                    .eventMessageQueue(eventQueue);

        } else {
            System.out.println("Builder could not be built!");
        }
        if(client == null) {
            client = builder.build();

            client.connect();
        } else {
            System.out.println("Client could not be initialized or the builder couldn't be built!");
        }

        while(!client.isDone()){
            //this is where we do shit

        }
        if(client.isDone()) {
            client.stop();
            System.out.println("Client: " + client.getName() + " has been stopped. Reason: Finished.");
        }
    }
}
