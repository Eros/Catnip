package io.gpm.Async;

import twitter4j.TwitterBase;
import twitter4j.api.SearchResource;
import twitter4j.api.TimelinesResources;
import twitter4j.api.TweetsResources;
import twitter4j.auth.OAuth2Support;
import twitter4j.auth.OAuthSupport;

import java.io.Serializable;

/***
 * @author George
 * @since 02/01/2018
 */
public interface AsyncTwitter extends Serializable,
        OAuth2Support,
        OAuthSupport,
        TwitterBase {
    //todo add async support
}
