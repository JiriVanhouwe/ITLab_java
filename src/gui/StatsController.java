package gui;

import java.io.IOException;

import domain.GuiSession;
import domain.Session;
import domain.SessionCalendarController;
import domain.SessionController;
import domain.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	private ObservableList<GuiSession> sessionList;
	
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
		updateSessionList();
		
		ChangeListener<GuiSession> changeListener = new ChangeListener<GuiSession>() {
			@Override
			public void changed(ObservableValue<? extends GuiSession> observable, //
                    GuiSession oldValue, GuiSession newValue) {
				// TODO Auto-generated method stub
				updateInfo(newValue);
			}
		};
		
		cb_sessions.getSelectionModel().selectedItemProperty().addListener(changeListener);
		
		
		}

	private void updateSessionList() {
		this.sessionList = FXCollections.observableList(sessionController.giveSessionsCurrentCalendar());
					
		cb_sessions.setItems(sessionList);
		
	}
	
	private void updateInfo(GuiSession session) {

		lbl_attendees.setText("aanwezige gebruikers");
		ObservableList<User> attendeeslist = FXCollections.observableList(session.getAttendees());
		if(attendeeslist.size()>0) {
			lv_attendees.setItems(attendeeslist);
			lv_attendees.setVisible(true);
			lbl_attendees.setVisible(true);
		}
		
		lbl_registeredUsers.setText("geristreerde gebruikers");
		ObservableList<User> registerdlist = FXCollections.observableList(session.getRegisteredUsers());
		if(registerdlist.size()>0) {
			lv_registeredUsers.setItems(registerdlist);
			lv_registeredUsers.setVisible(true);
			lbl_registeredUsers.setVisible(true);
		}
		
		//feedback ophalen
		//ObservableList<Feeback> feedbacklist = FXCollections.observableArrayList();
		lbl_feedbacktitle.setText("nog geen feedback ingegeven");
		lbl_feedbacktitle.setVisible(true);
	}
	
}
