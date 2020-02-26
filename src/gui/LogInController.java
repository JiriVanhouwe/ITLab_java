package gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LogInController extends AnchorPane{
	
	@FXML
    private TextField tfUser;

    @FXML
    private PasswordField pwUser;

    @FXML
    private Button btnLogIn;

    @FXML
    private Label passwordLost;

    @FXML
    private Label close;
	
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
