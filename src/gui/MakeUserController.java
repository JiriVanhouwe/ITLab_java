package gui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.UserController;
import domain.UserStatus;
import domain.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MakeUserController extends AnchorPane {

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
	private ComboBox<UserType> cmbUserType;

	@FXML
	private ComboBox<UserStatus> cmbUserStatus;
	
    @FXML
    private JFXButton btnGoBack;

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
		loadComboBoxes();
	}

	private void loadComboBoxes() {
		ObservableList<UserType> combo1 = FXCollections.observableArrayList(UserType.values());
		cmbUserType.getItems().addAll(combo1);

		ObservableList<UserStatus> combo2 = FXCollections.observableArrayList(UserStatus.values());
		cmbUserStatus.getItems().addAll(combo2);
	}

	@FXML
	void clickSave(MouseEvent event) {
		if(passwordValidation()) {
		try {	
			userContoller.createUser(txfFirstName.getText(), txfLastName.getText(), txfUserName.getText(),
			cmbUserType.getValue(), cmbUserStatus.getValue(), txfPassword.getText());

			lblMessage.setText("De gebruiker werd aangemaakt.");

		} catch (IllegalArgumentException e) {
			lblMessage.setText(e.getMessage());
		}
		} else lblMessage.setText("De wachtwoorden komen niet overeen.");
	}
	
    @FXML
    void clickGoBack(MouseEvent event) {

    }
	
	private boolean passwordValidation() {
		if(txfPassword.getText().equals(txfConfirmPassword.getText())) 
			return true;
		else
			return false;
		}
    
	}

