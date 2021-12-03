package twittergui;

import java.io.IOException;
import java.util.prefs.Preferences;

import bot.OAuthController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

public class SceneBuilder {

	public Scene getOAuthScene(Twitter twitter, RequestToken requestToken, Preferences prefs, Stage stage, String backupURL) {
		Parent root = null;
		Scene scene = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/OAuthScene.fxml"));
		OAuthController controller = new OAuthController(twitter, prefs, requestToken, stage, backupURL);
		loader.setController(controller);
		try {
			root = loader.load();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scene = new Scene(root);

		return scene;
	}

}
