package gui;

import java.io.IOException;

import domain.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LogInController extends AnchorPane {

    @FXML
    private TextField tfUser;

    @FXML
    private PasswordField pwUser;

    @FXML
    private Button btnLogIn;

    @FXML
    private Label passwordLost;

    @FXML
    private Text error_txt;

    @FXML
    private Label close;
	
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
	void loginBtnPressed(ActionEvent event) throws IOException {
		if(usercontroller.isUserPassComboValid(tfUser.getText(), pwUser.getText().toCharArray())) {
	        Scene scene = new Scene(new MainScreenController(new BeherenSessiekalenderController(), usercontroller));
	        getCurrentStage().setTitle("ITLab");
	        getCurrentStage().setMaximized(true);
	        getCurrentStage().setScene(scene);
		}else {
			error_txt.setText("Wrong password/username");
		}
	}
	
	private Stage getCurrentStage(){
        //Met deze methode kunnen we de huidige stage (het scherm) terug krijgen zodat we het gemakkelijk kunnen aanpassen
         return (Stage) tfUser.getScene().getWindow();
    }

}
