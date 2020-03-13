package gui;

import java.io.IOException;

import domain.UserController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class SettingsController extends AnchorPane{
	
	private UserController userController;

	public SettingsController(UserController userController) {
		this.userController = userController;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
	}

}
