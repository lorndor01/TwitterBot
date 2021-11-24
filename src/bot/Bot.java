package bot;


import twitter4j.Twitter;
import twitter4j.TwitterFactory;


public class Bot {
	//persistent OAuth token.
	private Twitter twitter;

	public Bot() {
		twitter = new TwitterFactory().getInstance();
		Authenticator auth = new Authenticator();
		auth.authenticate(twitter);
	}

	
}
