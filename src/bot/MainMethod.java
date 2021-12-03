package bot;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainMethod extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Bot bot = new Bot(primaryStage);
	}

}
