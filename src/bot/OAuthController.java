package bot;

import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class OAuthController{
	@FXML
	private Button submitButton;
	@FXML
	private TextField PINTextBox;
	
	private Twitter twitter;
	private Preferences prefs;
	private RequestToken requestToken;
	private Stage stage;
	
	public OAuthController(Twitter twitter, Preferences prefs, RequestToken requestToken, Stage stage) {
		this.twitter = twitter;
		this.prefs = prefs;
		this.requestToken = requestToken;
		this.stage = stage;
	}
	
	public void onSubmitButton() {
		AccessToken accessToken = null;
		String PIN = PINTextBox.getText();
		try {
			if(PIN.length()>0) { 
				accessToken= twitter.getOAuthAccessToken(requestToken, PIN);
			}
			else {
				accessToken = twitter.getOAuthAccessToken(requestToken);
			}
		}
		catch(TwitterException te) {
			if(te.getStatusCode()==401) {
				System.out.println("Failed to get an access token. Please check your PIN...");
			}
			else {
				te.printStackTrace();
			}
		}
		if(accessToken != null) {
			prefs.put("key", accessToken.getToken());
			prefs.put("secret", accessToken.getTokenSecret());
			System.out.println("YOURE IN!!!! YAY!!!!!");
			stage.hide();
		}
	}

}
