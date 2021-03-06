package gui;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXListView;

import domain.GuiSession;
import domain.GuiUser;
import domain.SessionController;
import domain.User;
import domain.UserController;
import domain.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StatsController extends SplitPane {

	// de items voor een geselecteerde session//
	@FXML
	private ChoiceBox<GuiSession> choiceBoxSession;

	@FXML
	private ChoiceBox<String> choiceBoxUser;

	@FXML
	private JFXListView<String> lvRegistered;

	@FXML
	private JFXListView<String> lvAttended;

	@FXML
	private Label lblRegistUsers;

	@FXML
	private Label lblAttendedUsers;
	
    @FXML
    private Button btnFeedback;
    
    @FXML
    private Button btn_mailAtendees;
	// tot hier voor geselecteerde session//

	// de items voor een geselecteerde user//
	@FXML
	private Label lblUserName;

	@FXML
	private Label lblFirstName;

	@FXML
	private Label lblLastName;

	@FXML
	private Label lblType;

	@FXML
	private Label lblStatus;

	@FXML
	private Label lblSelectedUserAttended;

	@FXML
	private Label lblSelectedUserAbsent;
	
    @FXML
    private Label lblFillUserName;

    @FXML
    private Label lblFillFirstName;

    @FXML
    private Label lblFillLastName;

    @FXML
    private Label lblFillType;

    @FXML
    private Label lblFillStatus;

    @FXML
    private Label lblFillRegistered;

    @FXML
    private Label lblFillAbsent;
	// tot hier voor geselecteerde user//
    
   

	private SessionController sessionController;
	private UserController userController;
	private GuiSession selectedSession;

	private ObservableList<GuiSession> sessionList;

	public StatsController(SessionController sessionController, UserController userController) {
		this.sessionController = sessionController;
		this.userController = userController;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		loadChoiceBoxes();

		choiceBoxUser.getSelectionModel().selectedItemProperty().addListener((obsVal, oldVal, newVal) -> {
			if (newVal != null) {
				updateUserStatistics(newVal);
			}
		});
		
		choiceBoxSession.getSelectionModel().selectedItemProperty().addListener((obsVal, oldVal, newVal) -> {
			if (newVal != null) {
				updateSessionStatistics(newVal);
			}
		});
	}

	private void updateUserStatistics(String userName) {
		placeLabelsVisible();
		User user = (User) userController.giveUser(userName);
		lblFillUserName.setText(user.getUserName());
		lblFillFirstName.setText(user.getFirstName());
		lblFillLastName.setText(user.getLastName());
		lblFillStatus.setText(user.giveUserStatus());
		lblFillType.setText(user.giveUserType());
		int registered = userController.countRegisterdFromUser(userName);
		int atend = userController.countAttendeesFromUser(userName);
		int absent = registered - atend;
		
		lblFillAbsent.setText(String.format("%d",absent));
		lblFillRegistered.setText(String.format("%d",atend));

	}

	private void updateSessionStatistics(GuiSession session) {
		placeListViewsVisible();

		selectedSession = session;
		List<User> registUsers = selectedSession.getRegisteredUsers().stream().sorted(Comparator.comparing(User::getUserName)).collect(Collectors.toList());
		List<User> attendUsers = selectedSession.getAttendees().stream().sorted(Comparator.comparing(User::getUserName)).collect(Collectors.toList());;
		
		lvRegistered.setItems(FXCollections.observableArrayList(registUsers.stream().map(el -> el.getUserName()).collect(Collectors.toList())));
		lvAttended.setItems(FXCollections.observableArrayList(attendUsers.stream().map(el -> el.getUserName()).collect(Collectors.toList())));
		
		lblAttendedUsers.setText(String.format("Aanwezigen: %d", attendUsers.size()));
		lblRegistUsers.setText(String.format("Geregistreerden: %d", registUsers.size()));
		
		 if(attendUsers.size() < 1)  {
			 btn_mailAtendees.setDisable(true);
			 btn_mailAtendees.setVisible(false);
		 }
		 else {
			 btn_mailAtendees.setDisable(false);
			 btn_mailAtendees.setVisible(true);
		 }
			
	}
	
    @FXML
    void clickFeedback(MouseEvent event) {  	
		Scene scene = new Scene(new FeedbackController(selectedSession,sessionController));	
		Stage stage = new Stage();

		stage.setTitle("ITLab");
		stage.setScene(scene);
		stage.setResizable(false);
		//This makes sure the new stage stays on top and can't be sent to the background
		stage.setAlwaysOnTop(true);
		stage.showAndWait();
    }
    
    @FXML
    void clickMailTo(MouseEvent event) {
    	Scene scene = new Scene(new MailToController(selectedSession));	
		Stage stage = new Stage();

		stage.setTitle("ITLab");
		stage.setScene(scene);
		stage.setResizable(false);
		//This makes sure the new stage stays on top and can't be sent to the background
		stage.setAlwaysOnTop(true);
		stage.showAndWait();
    }

	private void loadChoiceBoxes() {
		setChoiceBoxSession();
		choiceBoxUser.setItems(convertUserToStringChoiceBox());
	}

	// sessie omzetten naar datum + naam van de sessie
	private void setChoiceBoxSession() { 
		 List<GuiSession> listSessions = sessionController.giveSessionsClosedAndFinshedCurrentCalendar().stream().sorted(Comparator.comparing(GuiSession::getDate)).collect(Collectors.toList());
		 choiceBoxSession.setItems(FXCollections.observableArrayList(listSessions));
		 //Een verantwoordelijke mag enkel zijn eigen sessies zien
		 if(!userController.giveLoggedInUser().getUserType().equals(UserType.HEAD)) {
			 listSessions.stream().filter(s -> s.getHost().getUserName().equals(userController.giveLoggedInUser()));
		 }
		 
		 if(listSessions.size() < 1)
		 	{ 	
		 		Alert alert = new Alert(AlertType.INFORMATION);
		 		alert.setContentText("nog geen data beschikbaar");
		 		alert.showAndWait();
		 	}
	}

	// users omzetten naar voornaam + familienaam
	private ObservableList<String> convertUserToStringChoiceBox() { 
		ObservableList<GuiUser> list = userController.giveAllUsers();
		List<String> listUsers = list.stream().sorted(Comparator.comparing(GuiUser::getUserName))
				.map(el -> el.getUserName()).collect(Collectors.toList());
		return FXCollections.observableArrayList(listUsers);
	}
	
	//hulpmethode: zet de labels visible zodra een user geselecteerd wordt.
	private void placeLabelsVisible() {
		lblUserName.setVisible(true);
		lblFirstName.setVisible(true);
		lblLastName.setVisible(true);
		lblStatus.setVisible(true);
		lblType.setVisible(true);
		lblSelectedUserAbsent.setVisible(true);
		lblSelectedUserAttended.setVisible(true);
	
	}
	
	//hulpmethode: zet listviews en knop visible zodra een sessie geselecteerd wordt
	private void placeListViewsVisible() {
		btnFeedback.setVisible(true);
		lvAttended.setVisible(true);
		lvRegistered.setVisible(true);
		lblRegistUsers.setVisible(true);
		lblAttendedUsers.setVisible(true);
		btn_mailAtendees.setVisible(true);
	}

}
