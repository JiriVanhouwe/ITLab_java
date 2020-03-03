package gui;

import java.io.IOException;
import java.util.Optional;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import domain.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LogInController extends AnchorPane {

	@FXML
	private JFXTextField tfUser;

	@FXML
	private Button btnLogIn;

	@FXML
	private Label lblPasswordLost;

	@FXML
	private JFXPasswordField tfPassword;

	@FXML
	private Label txt_error;

	private UserController usercontroller;

	public LogInController(UserController usercontroller) {
		this.usercontroller = usercontroller;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@FXML
	void forgetPasswordClick(MouseEvent event) {
		TextInputDialog input = new TextInputDialog();
		input.setTitle("Wachtwoord vergeten");
		input.setHeaderText("Geef jouw e-mailadres in:");
		input.setContentText("E-mailadres:");

		Optional<String> result = input.showAndWait();
		String emailadres = result.get();
		// TODO
		// als het emailadres gekend is in de DB: "Je kan een nieuw wachtwoord instellen
		// via jouw e-mail".
		// als het emailadres niet gekend is in de DB: "Dit e-mailadres is ongekend."
		if (!emailadres.isBlank())
			txt_error.setText("Een nieuw wachtwoord werd naar\njouw e-mailadres verzonden.");
		else
			txt_error.setText("Het opgegeven e-mailadres is\nniet gekend.");
	}

	@FXML
	void loginBtnPressed(ActionEvent event) throws IOException {

		if (usercontroller.isUserPassComboValid(tfUser.getText(), tfPassword.getText().toCharArray())) {
			//Sluit huidig scherm
			Stage curStage = (Stage) tfUser.getScene().getWindow();
			curStage.close();
			
			//Open nieuw scherm
			Scene scene = new Scene(new MainScreenController(usercontroller));

			Parent root = scene.getRoot();
			Stage stage = new Stage();
			stage.setOnShown((WindowEvent t) -> {
				stage.setMinWidth(700);
				stage.setMinHeight(700);
			});

			stage.setTitle("ITLab");
			stage.setHeight(1080);
			stage.setMaximized(true);
			stage.setScene(scene);
			stage.show();
		} else {
			txt_error.setText("Verkeerde gebruikersnaam of wachtwoord.");
		}
	}

}
