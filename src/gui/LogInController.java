package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.CalendarView;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    	//TODO
    	//als het emailadres gekend is in de DB: "Je kan een nieuw wachtwoord instellen via jouw e-mail".
    	//als het emailadres niet gekend is in de DB: "Dit e-mailadres is ongekend." 	
    	if(!emailadres.isBlank())
    		txt_error.setText("Een nieuw wachtwoord werd naar\njouw e-mailadres verzonden.");
    	else
    		txt_error.setText("Het opgegeven e-mailadres is\nniet gekend.");
    }

	@FXML
	void loginBtnPressed(ActionEvent event) throws IOException {
        
		if(usercontroller.isUserPassComboValid(tfUser.getText(), tfPassword.getText().toCharArray())) {
	        Scene scene = new Scene(new DashboardController(usercontroller));
	        
	        getCurrentStage().setTitle("ITLab");
	        getCurrentStage().setMaximized(true);
	        getCurrentStage().setScene(scene);
		} else {
			txt_error.setText("Verkeerde gebruikersnaam of wachtwoord.");
		}
	}
	

	
	private Stage getCurrentStage(){
        //Met deze methode kunnen we de huidige stage (het scherm) terug krijgen zodat we het gemakkelijk kunnen aanpassen
         return (Stage) tfUser.getScene().getWindow();
    }
	
}
