package gui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import domain.ITLab;
import domain.SessionCalendarController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MakeSessionCalendarController extends AnchorPane {
	@FXML
	private DatePicker calStartDate;
	@FXML
	private DatePicker calEndDate;
	@FXML
	private JFXButton btnSave;
    @FXML
    private Label lblMessage;
	
	private SessionCalendarController sessionCalendarController;
	
	public MakeSessionCalendarController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MakeSessionCalendar.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		sessionCalendarController = new SessionCalendarController();
	}

	// Event Listener on JFXButton[#btnSave].onMouseClicked
	@FXML
	public void clickSave(MouseEvent event) {
		int id = Integer.parseInt((Integer.toString(calStartDate.getValue().getYear()) + Integer.toString(calEndDate.getValue().getYear())));
		
		if (sessionCalendarController.doesSessionCalendarExist(id)) {
//			Alert a = new Alert(AlertType.ERROR);
//			a.setTitle("Sessiekalender aanmaken gefaald");
//			a.setContentText("Er bestaat al een sessiekalender in het schooljaar " + id);
//			a.showAndWait();
    		lblMessage.setText("Er bestaat al een sessiekalender in het schooljaar " + id);
			
		} else {
			sessionCalendarController.createSessionCalendar(id, calStartDate.getValue(), calEndDate.getValue());
		}
	}
}
