package main;

import gui.BeherenSessiekalenderController;
import gui.MainScreenController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StartUpGui extends Application {

	@Override
	public void start(Stage stage) {
	        Scene scene = new Scene(new MainScreenController(new BeherenSessiekalenderController()));
	        stage.setTitle("Log in");
	        stage.setScene(scene);

	        // The stage will not get smaller than its preferred (initial) size.
	        stage.setOnShown((WindowEvent t) -> {
	            stage.setMinWidth(stage.getWidth());
	            stage.setMinHeight(stage.getHeight());
	        });
	        stage.show();
	        stage.setMaximized(true);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
