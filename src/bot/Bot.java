package bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Bot {
	//persistent OAuth token.
	private Twitter twitter;

	public Bot() {
		twitter = new TwitterFactory().getInstance();
		Authenticator auth = new Authenticator();
		auth.authenticate(twitter);
	}

	
}
