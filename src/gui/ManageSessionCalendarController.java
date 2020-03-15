package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import domain.Session;
import domain.SessionCalendar;
import domain.SessionCalendarController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ManageSessionCalendarController {

	   @FXML
	    private JFXTextField txtAcademicYear;

	    @FXML
	    private JFXDatePicker txtStartDate;

	    @FXML
	    private JFXDatePicker txtEndDate;

	    @FXML
	    private Button btnConfirm;

	    @FXML
	    private TableView<SessionCalendar> tblSessionCalendars;

	    @FXML
	    private TableColumn<SessionCalendar, Integer> colAcademicYear;

	    @FXML
	    private TableColumn<SessionCalendar, LocalDate> colStartEndDate;

	    @FXML
	    private TableView<Session> calSessions;

	    @FXML
	    private TableColumn<Session, String> colSessionTitle;

	    @FXML
	    private TableColumn<Session, LocalDate> colSessionDate;

	    @FXML
	    private TableColumn<Session, LocalTime> colSessionStart;

	    @FXML
	    private TableColumn<Session, LocalTime> colSessionEnd;
	    
	    private SessionCalendarController sessionCalendarController;
	    private SessionCalendar selectedSessionCalendar;
	    
	    public ManageSessionCalendarController() {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageSessionCalendar.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			try {
				loader.load();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			
			sessionCalendarController = new SessionCalendarController();
			selectedSessionCalendar = sessionCalendarController.giveSessionCalendar();
			
			txtAcademicYear.setText("NOG IN KLASSE SESSIONCALENDAR STEKEN");
			txtStartDate.setValue(selectedSessionCalendar.getStartDate());
			txtEndDate.setValue(selectedSessionCalendar.getEndDate());
			
		}
}
