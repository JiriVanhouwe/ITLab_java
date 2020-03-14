package gui;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import domain.Session;
import domain.SessionController;
import domain.User;
import domain.UserController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainScreenController extends SplitPane {

	@FXML
    private Label lblUserName;

    @FXML
    private HBox hbox_mainSection;
    
    @FXML
    private SplitPane rootPane;
    
    @FXML
    private Button btnUsers;
    
    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignOut;
    
    @FXML
    private Button btnCalendar;

    @FXML
    private Button btnSessions;

    @FXML
    private Button btnStatistics;

    private UserController usercontroller;
    
    private SessionController sessionController;
    
    private UsersController uc;
    
    private Button _selectedButton;
    
    public MainScreenController(UserController usercontroller) {
    	this.usercontroller = usercontroller;
    	sessionController = new SessionController();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
    	loader.setController(this);
    	loader.setRoot(this);
    	
    	try{
    	      loader.load();
    	} 
    	catch (IOException ex){
    	      throw new RuntimeException(ex);
    	}  
    	
    	
    	
    	lblUserName.setText(usercontroller.giveLoggedInUser().getFirstName());
    	initializeScreen();
    	_selectedButton = btnCalendar;
    	changeMainSection(new CalendarController());
    }
    
//    private void initializeCalendar() {
//		CalendarView calendarView = new CalendarView();
//		calendarView.setShowAddCalendarButton(false); // make sure it is not possible to add multiple calendars in a calendarview
//		calendarView.setShowPrintButton(false); // make the printing option invisible
//	
//		
//		Calendar calendar1 = new Calendar("Sessies"); 
//
//        calendar1.setStyle(Style.STYLE2);
//
//        calendarView.setEntryDetailsPopOverContentCallback(param -> new BeherenSessieController(param.getEntry()));
//        
//
//        CalendarSource myCalendarSource = new CalendarSource("My Calendars"); 
//        myCalendarSource.getCalendars().add(calendar1);
//
//        calendarView.getCalendarSources().addAll(myCalendarSource); 
//
//        calendarView.setRequestedTime(LocalTime.now());
//
//        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
//                @Override
//                public void run() {
//                        while (true) {
//                                Platform.runLater(() -> {
//                                        calendarView.setToday(LocalDate.now());
//                                        calendarView.setTime(LocalTime.now());
//                                });
//
//                                try {
//                                        // update every 10 seconds
//                                        sleep(10000);
//                                } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                }
//                        }
//                };
//        };
//        
//        linkSessionsToEntries(calendar1);
//
//        Entry<String> session1 = new Entry<>("Sessie 1");
//        session1.changeStartDate(LocalDate.now().plusDays(1));
//        session1.changeStartTime(LocalTime.now().plusHours(1));
//        session1.changeEndTime(LocalTime.now().plusHours(6));
//        session1.changeEndDate(LocalDate.now().plusDays(1));
//        calendar1.addEntry(session1);
//
//        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
//        updateTimeThread.setDaemon(true);
//        updateTimeThread.start();
//        
//
//    	_selectedButton = btnCalendar;
//    	changeMainSection(calendarView);
//	}
//    
//    private void linkSessionsToEntries(Calendar calendar) {
//    	List<Session> sessions = this.sessionController.giveSessionsCurrentCalendar();
//    	
//    	sessions.stream().forEach(session -> {
//    		Entry entry = new Entry();
//    		entry.setId(Integer.toString(session.getSessionID()) + "#");
//    		entry.setTitle(session.getTitle());
//    		entry.setInterval(session.getDate().atTime(session.getStartHour()), session.getDate().atTime(session.getEndHour()));
//    		calendar.addEntry(entry);
//    	});
//    }
    
    private void changeMainSection(Node node) {
    	if(hbox_mainSection.getChildren().size() > 0)
    		hbox_mainSection.getChildren().remove(0);
    	
    	HBox.setHgrow(node, Priority.ALWAYS);
    	hbox_mainSection.getChildren().add(node);
    }

	private void initializeScreen() {
		User user = usercontroller.giveLoggedInUser();
	}
	
    @FXML
    void userNameClick(MouseEvent event) {
    	hbox_mainSection.getChildren().clear();
    	SettingsController uc = new SettingsController(this.usercontroller);
    	hbox_mainSection.getChildren().add(uc);
    }
    @FXML
    void clickBtnSettings(MouseEvent event) {
    	userNameClick(event);
    }
    
    @FXML
    void clickBtnUsers(MouseEvent event) {  	
    	_selectedButton.getStyleClass().remove("selected");
    	btnUsers.getStyleClass().add("selected");
    	_selectedButton = btnUsers;
    	
    	
    	hbox_mainSection.getChildren().clear();
    	uc = new UsersController(this.usercontroller);
    	hbox_mainSection.getChildren().add(uc);
    }
    
    @FXML
    void clickSignOut(MouseEvent event) {
    	//Toon het login scherm
    	//Sluit huidig scherm
		Stage curStage = (Stage) btnSignOut.getScene().getWindow();
		curStage.close();
		
		//Open nieuw scherm
		Scene scene = new Scene(new LogInController(usercontroller));

		Parent root = scene.getRoot();
		Stage stage = new Stage();
		UserController usercontroller = new UserController();

		stage.getIcons().add(new Image("/resources/ITLAB_logo_round.png"));
		stage.setTitle("Log in");
		stage.setScene(scene);

		
		// The stage will not get smaller than its preferred (initial) size.
		stage.setOnShown((WindowEvent t) -> {
			stage.setMinWidth(stage.getWidth());
			stage.setMinHeight(stage.getHeight());
		});
		
		
		stage.initStyle(StageStyle.UNDECORATED); // heel mooi effect, maar we moeten er nog in slagen het op het hoofdscherm terug te veranderen naar DECORATED
		stage.show();
    }

}
