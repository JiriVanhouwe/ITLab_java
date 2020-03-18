package gui;

import java.io.IOException;

import domain.SessionCalendarController;
import domain.SessionController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ChoiceBox;

public class StatsController extends GridPane{
	@FXML
	private ChoiceBox cb_sessions;
	@FXML
	private Label lbl_sessieinfo;
	@FXML
	private Label lbl_attendeesstats;
	@FXML
	private Label lbl_registeredUsers;
	@FXML
	private Label lbl_attendees;
	@FXML
	private ListView lv_registeredUsers;
	@FXML
	private ListView lv_attendees;
	@FXML
	private ChoiceBox cb_feedback;
	@FXML
	private Button btn_feedbackDelete;
	@FXML
	private Label lbl_feedbacktitle;
	@FXML
	private Label lbl_feedbackinfo;

	private SessionController sessionController;
	private SessionCalendarController sessionCalendarController;
	
	public StatsController(SessionController sessionController) {
		this.sessionController = sessionController;
		//this.sessionCalendarController = sessionCalendarController;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		cb_sessions.setItems(FXCollections.observableList(sessionController.giveSessionsCurrentCalendar()));
	}
}
