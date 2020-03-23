package gui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;

import domain.GuiUser;
import domain.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SettingsController extends SplitPane{
	
	private UserController userController;
	
    @FXML
    private Label lblUserName;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblType;
    
    @FXML
    private Label lblMessage;

    @FXML
    private JFXPasswordField lblPassword;

    @FXML
    private JFXPasswordField txfNewPassword;

    @FXML
    private JFXPasswordField txfConfirmPassword;

    @FXML
    private JFXButton btnSave;


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
		setUserSettings();
		setTooltips();	
	}
	
    private void setUserSettings() {
    	GuiUser user = userController.giveLoggedInUser();
    	lblFirstName.setText(user.getFirstName());
    	lblLastName.setText(user.getLastName());
    	lblUserName.setText(user.getUserName());
    	lblType.setText(user.giveUserType());
	}

	private void setTooltips() { 
    	//TODO deze verschijnen nog niet! Waarom weet ik niet.
    	lblUserName.setTooltip(new Tooltip("Gebruikersnaam kan je niet wijzigen."));
    	lblFirstName.setTooltip(new Tooltip("Voornaam kan je niet wijzigen."));
    	lblLastName.setTooltip(new Tooltip("Familienaam kan je niet wijzigen."));
    	lblType.setTooltip(new Tooltip("Type gebruiker kan je niet wijzigen."));
	}

	@FXML
    void clickSave(MouseEvent event) {
		if(txfNewPassword == null || txfConfirmPassword == null || txfNewPassword.getText().isBlank() || txfConfirmPassword.getText().isBlank())
			lblMessage.setText("Gelieve beide wachtwoordvelden in te vullen.");
		else 
			passwordValidation();			
    }
	
	private boolean passwordValidation() {
		if(txfNewPassword.getText().equals(txfConfirmPassword.getText())) {
			userController.changePassword(lblUserName.getText(), txfConfirmPassword.getText());
			lblMessage.setText("Je wachtwoord werd gewijzigd.");
			return true;
		}
		else {
			lblMessage.setText("De nieuwe wachtwoorden komen niet overeen.");
			return false;
		}
	}
//    @FXML
//    void clickEditSessionCalendars(MouseEvent event) {
//    	if(hbox_mainSection.getChildren().size() > 0)
//    		hbox_mainSection.getChildren().remove(0);
//    	
//    	HBox.setHgrow(node, Priority.ALWAYS);
//    	hbox_mainSection.getChildren().add(node);
//    }

}
