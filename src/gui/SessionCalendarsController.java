package gui;

import java.io.IOException;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;

import domain.GuiSessionCalendar;
import domain.SessionCalendarController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SessionCalendarsController extends AnchorPane {

    @FXML
    private Button btnAddSessionCalendar;

    @FXML
    private JFXButton btnSave;
	
    @FXML
    private DatePicker calStartDate;

    @FXML
    private DatePicker calEndDate;

    @FXML
    private ComboBox<Integer> cboSessionCalendars;
    
    @FXML
    private Label lblMessage;

    
	private SessionCalendarController sessionCalendarController;
	
	private GuiSessionCalendar _sessionCalendar;

	public SessionCalendarsController() {
		sessionCalendarController = new SessionCalendarController();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SessionCalendars.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		cboSessionCalendars.getItems().addAll(FXCollections.observableArrayList(sessionCalendarController
																						.giveSessionCalendars()
																						.stream()
																						.map(sc -> sc.getId())
																						.collect(Collectors.toList())));
	}

    @FXML
    void addSessionCalendar(MouseEvent event) {
    	
		Scene scene = new Scene(new MakeSessionCalendarController());
		
		Stage stage = new Stage();

		stage.getIcons().add(new Image("/resources/ITLAB_logo_round.png"));
		stage.setTitle("ITLab");
		stage.setScene(scene);
		stage.setResizable(false);
		//stage.initStyle(StageStyle.UNDECORATED);
		
		stage.setAlwaysOnTop(true);
		stage.showAndWait();
		
    }

    @FXML
    void clickSave(MouseEvent event) {
    	int id = 0;
		lblMessage.setText("");
    	try {
    		
        	id = cboSessionCalendars.getValue();
        	sessionCalendarController.editSessionCalendar(id, calStartDate.getValue(), calEndDate.getValue());
        	lblMessage.setText("Sessiekalender is aangepast.");
    	} catch (NullPointerException e) {
    		lblMessage.setText("Er is geen sessiekalender geselecteerd");
    		
    	}
    }
	
    @FXML
    void changeAcademicYear(ActionEvent event) {
    	int id = cboSessionCalendars.getValue();
    	_sessionCalendar = sessionCalendarController.giveSessionCalendarById(id);
    	
    	calStartDate.setValue(_sessionCalendar.getStartDate());
    	calEndDate.setValue(_sessionCalendar.getEndDate());
    }

}
