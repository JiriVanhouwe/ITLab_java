package gui;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import domain.UserController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
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
	
    @FXML
    private Label lblClose;

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
		
		if(emailadres == null || emailadres.isBlank())
			txt_error.setText("Gelieve jouw e-mailadres in te geven.");
		else
			checkEmailAdress(emailadres);		
	}
	
	public void checkEmailAdress(String email) {
		if(usercontroller.findEmailAddress(email) == null)
			txt_error.setText("Het opgegeven e-mailadres is\nniet gekend.");
		else
		{
			//TODO zend email
			txt_error.setText("Een nieuw wachtwoord werd naar\njouw e-mailadres verzonden.");
		}
			
	}

	@FXML
	void loginBtnPressed(ActionEvent event) throws IOException {
		
		//Wachtwoord hashen
		String generatedPassword;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(tfPassword.getText().getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
		}catch(NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
		
		if (usercontroller.isUserPassComboValid(tfUser.getText(), generatedPassword)) {
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

			stage.getIcons().add(new Image("/resources/ITLAB_logo_round.png"));
			stage.setTitle("ITLab");
			stage.setHeight(1080);
			stage.setMaximized(true);
			stage.setScene(scene);
			stage.show();
			
		} else {
			txt_error.setText("Verkeerde gebruikersnaam \nof wachtwoord.");
		}
	}
	

    @FXML
    void close(MouseEvent event) {
    	Platform.exit();
    }

}
