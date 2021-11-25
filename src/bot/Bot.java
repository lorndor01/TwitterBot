package bot;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import exceptions.PostTooLongException;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


public class Bot {
	//persistent OAuth token.
	private Twitter twitter;

	public Bot() {
		twitter = new TwitterFactory().getInstance();
		Authenticator auth = new Authenticator();
		auth.authenticate(twitter);   
		String message = "This is my dog";
		try {
			File file = new File("src/bot/media/dog.jpg");
			post(message, file);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PostTooLongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
