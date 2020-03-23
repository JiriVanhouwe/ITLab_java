package gui;

import java.io.IOException;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;

import domain.GuiSessionCalendar;
import domain.SessionCalendarController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    
	private SessionCalendarController sessionCalendarController;

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
    	
    }

    @FXML
    void clickSave(MouseEvent event) {

    }
	

}
