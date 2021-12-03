package bot;

import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class OAuthController{
	@FXML
	private Button submitButton;
	@FXML
	private Button copyLinkButton;
	@FXML
	private TextField PINTextBox;

	private String URL;
	private Twitter twitter;
	private Preferences prefs;
	private RequestToken requestToken;
	private Stage stage;

	public OAuthController(Twitter twitter, Preferences prefs, RequestToken requestToken, Stage stage, String URLStr) {
		this.twitter = twitter;
		this.prefs = prefs;
		this.requestToken = requestToken;
		this.stage = stage;
		this.URL = URLStr;
	}
	public void copyLink() {
		resetToInitialStage();
		Clipboard cb = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		boolean stored = content.putString(URL);
		boolean copied = cb.setContent(content);
		if(!stored || !copied) {
			copyLinkButton.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, null)));
		}
		else {
			copyLinkButton.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, null, null)));
		}
	}

	public void onSubmitButton() {
		resetToInitialStage();
		AccessToken accessToken = null;
		String PIN = PINTextBox.getText();
		try {
			if(PIN.length()>0) {
				accessToken= twitter.getOAuthAccessToken(requestToken, PIN);
			}
			else {
				PINTextBox.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, null)));
				return;
			}
		}
		catch(TwitterException te) {
			if(te.getStatusCode()==401) {
				System.out.println("Failed to get an access token. Please check your PIN...");
				return;
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

	private void resetToInitialStage() {
		PINTextBox.setBorder(null);
		submitButton.setBorder(null);
		copyLinkButton.setBorder(null);
	}

}
