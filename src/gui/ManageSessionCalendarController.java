package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import com.jfoenix.controls.JFXTextField;

import domain.Session;
import domain.SessionCalendar;
import domain.SessionCalendarController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class ManageSessionCalendarController extends VBox{

	   @FXML
	    private JFXTextField txtAcademicYear;

	    @FXML
	    private DatePicker dpStartDate;

	    @FXML
	    private DatePicker dpEndDate;

	    @FXML
	    private Button btnConfirm;

	    @FXML
	    private TableView<SessionCalendar> tblSessionCalendars;

	    @FXML
	    private TableColumn<SessionCalendar, Integer> colAcademicYear;

	    @FXML
	    private TableColumn<SessionCalendar, LocalDate> colStartEndDate;

	    @FXML
	    private TableView<Session> tblSessions;

	    @FXML
	    private TableColumn<Session, String> colSessionTitle;

	    @FXML
	    private TableColumn<Session, String> colSessionDate;

	    @FXML
	    private TableColumn<Session, String> colSessionStart;

	    @FXML
	    private TableColumn<Session, String> colSessionEnd;
	    
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
			dpStartDate.setValue(selectedSessionCalendar.getStartDate());
			dpEndDate.setValue(selectedSessionCalendar.getEndDate());
			
			colSessionTitle.setCellValueFactory(data -> data.getValue().sessionTitleProperty());
			colSessionDate.setCellValueFactory(data -> data.getValue().sessionDateProperty());
			colSessionStart.setCellValueFactory(data -> data.getValue().sessionStartHourProperty());
			colSessionEnd.setCellValueFactory(data -> data.getValue().sessionEndHourProperty());
			
			tblSessions.setItems(FXCollections.observableArrayList(sessionCalendarController.giveSessionsCurrentMonth()));
			
		}
}