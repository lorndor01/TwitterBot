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

	public Scene getOAuthScene(Twitter twitter, RequestToken requestToken, Preferences prefs, Stage stage) {
		Parent root;
		Scene scene = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/OAuthScene.fxml"));
			OAuthController controller = new OAuthController(twitter, prefs, requestToken, stage);
			loader.setController(controller);
			root = loader.load();
			scene = new Scene(root);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return scene;
	}

}
