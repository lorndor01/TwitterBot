package bot;


import java.io.File;

import exceptions.PostTooLongException;
import javafx.stage.Stage;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


public class Bot {
	//persistent OAuth token.
	private Twitter twitter;
	private Stage stage;
	public final static String TWITTER_BOT_ICON_PATH = "bot/media/twitter_bot.png";
	public Bot(Stage stage) {
		this.stage = stage;
		twitter = new TwitterFactory().getInstance();
		Authenticator auth = new Authenticator();
		auth.authenticate(twitter, stage);
		String message = "This is my dog";

	}

	public void post(String message) throws TwitterException, PostTooLongException{
		File defaultMedia = null;
		post(message, defaultMedia);
	}
	public void post(String message, File file) throws TwitterException, PostTooLongException{
		if(message.length()>280) {
			throw new PostTooLongException("Your post is too long. Twitter's max tweet length is 280 characters.");
		}
		else {
			StatusUpdate su = new StatusUpdate(message);
			if(file!=null) {
				su.setMedia(file);
			}
			twitter.updateStatus(su);
		}
	}

}
