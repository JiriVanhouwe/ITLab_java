package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.UserController;
import domain.UserStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
	private ComboBox<String> cmbUserType;

	@FXML
	private ComboBox<String> cmbUserStatus;

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
		List<String> types = new ArrayList<>();
		types.add("Hoofdverantwoordelijke");
		types.add("Verantwoordelijke");
		types.add("Gebruiker");
		List<String> status = new ArrayList<>();
		status.add("Actief");
		status.add("Geblokkeerd");
		status.add("Niet-actief");

		ObservableList<String> combo1 = FXCollections.observableArrayList(types);
		cmbUserType.getItems().addAll(combo1);

		ObservableList<String> combo2 = FXCollections.observableArrayList(status);
		cmbUserStatus.getItems().addAll(combo2);
	}

	@FXML
	void clickSave(MouseEvent event) {
		if (cmbUserType.getValue() == null || cmbUserStatus.getValue() == null)
			lblMessage.setText("Selecteer een type en status van de gebruiker.");
		else {
			if (passwordValidation()) {
				try {
					System.out.println(cmbUserType.getValue());
					userContoller.createUser(txfFirstName.getText(), txfLastName.getText(), txfUserName.getText(),
							userContoller.stringToUserType(cmbUserType.getValue()),
							userContoller.stringToUserStatus(cmbUserStatus.getValue()), txfPassword.getText());

					lblMessage.setText("De gebruiker werd aangemaakt.");

				} catch (IllegalArgumentException e) {
					lblMessage.setText(e.getMessage());
				}
			} else
				lblMessage.setText("De wachtwoorden komen niet overeen.");
		}
	}

	@FXML
	void clickGoBack(MouseEvent event) {
		Stage stage = (Stage) getScene().getWindow();
		stage.close();
	}

	private boolean passwordValidation() {
		return txfPassword.getText().equals(txfConfirmPassword.getText());
	}
}
