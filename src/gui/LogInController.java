package gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

public class LogInController extends GridPane{
	
	public LogInController() {
		 // this.domeinController = domeinController;
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
	        loader.setRoot(this);
	        loader.setController(this);
	        try {
	            loader.load();
	        } catch (IOException ex) {
	            throw new RuntimeException(ex);
	        }
	}

}
