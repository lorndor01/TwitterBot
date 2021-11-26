package bot;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twittergui.SceneBuilder;




public class Authenticator {
	@FXML
	private Button submitButton;
	@FXML
	private TextField PINTextBox;
	public void authenticate(Twitter twitter, Stage stage) {
		//pull the key and secet from preferences
		Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
		try {
			prefs.clear();
		} catch (BackingStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//If user hasn't been authenticated then key & secret will be null.
		String key = prefs.get("key", null);
		String secret = prefs.get("secret", null);
		AccessToken accessToken = null;
		twitter.setOAuthConsumer("7ezq8w1gWS92oBiK4OrGhEhrW", 
				"oMPrrqn0LBsL6nrrbE2sUf2QKy3d1LWkJrrekrMsIHGJbuUMBs"); //programs key and secret
		if(key!=null && secret != null) {
			//User has already been authenticated
			accessToken = new AccessToken(key, secret);
			twitter.setOAuthAccessToken(accessToken); 
		}
 
		/*
		 * By now we either have a completed OAuth sequence and null AccessToken OR
		 * we have a valid non-null AccessToken
		 */
		try {
			//If request token is already avaiable then an IllegalStateException will be thrown
			RequestToken requestToken = twitter.getOAuthRequestToken();
			SceneBuilder sb = new SceneBuilder();
			Scene scene = sb.getOAuthScene(twitter, requestToken, prefs, stage);
			stage.setScene(scene);
			stage.show();
			if(accessToken == null) {
				try {
					Desktop.getDesktop().browse(new URI(requestToken.getAuthenticationURL()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		catch(IllegalStateException e) {
			// access token is already available, or consumer key/secret is not set.
            if (!twitter.getAuthorization().isEnabled()) {
                System.out.println("OAuth consumer key/secret is not set.");
                System.exit(-1);
            }

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
