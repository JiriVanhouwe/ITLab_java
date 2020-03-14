package gui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import domain.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MakeUserController extends AnchorPane{
	
	private UserController userContoller;
	
    @FXML
    private JFXButton btnSave;

    @FXML
    private Label lblMessage;

    @FXML
    private JFXPasswordField txfPassword;

    @FXML
    private JFXPasswordField txfConfirmPassword;

    @FXML
    private JFXTextField txfUserName;

    @FXML
    private JFXTextField txfFirstName;

    @FXML
    private JFXTextField txfLastName;

    @FXML
    private JFXComboBox<String> cmbUserType;

    @FXML
    private JFXComboBox<String> cmbUserStatus;
	
	
	public MakeUserController(UserController userController) {		
		this.userContoller = userController;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MakeUser.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	

    @FXML
    void clickSave(MouseEvent event) {

    }

}
