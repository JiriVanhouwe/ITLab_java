package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.controlsfx.control.PopOver;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.view.CalendarView;

import domain.ITLab;
import domain.UserController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.CalendarApp;

public class LogInController extends AnchorPane {

    @FXML
    private TextField tfUser;

    @FXML
    private PasswordField pwUser;

    @FXML
    private Button btnLogIn;

    @FXML
    private Label passwordLost;

    @FXML
    private Text error_txt;

    @FXML
    private Label close;
	
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
	void loginBtnPressed(ActionEvent event) throws IOException {
        CalendarView calendarView = new CalendarView(); 
        
		if(usercontroller.isUserPassComboValid(tfUser.getText(), pwUser.getText().toCharArray())) {
	        Scene scene = new Scene(new MainScreenController(calendarView, usercontroller));
	        
	        
	        generateCalendar(calendarView);
	        
	        
	        getCurrentStage().setTitle("ITLab");
	        getCurrentStage().setMaximized(true);
	        getCurrentStage().setScene(scene);
		}else {
			error_txt.setText("Wrong password/username");
		}
	}
	
	private Stage getCurrentStage(){
        //Met deze methode kunnen we de huidige stage (het scherm) terug krijgen zodat we het gemakkelijk kunnen aanpassen
         return (Stage) tfUser.getScene().getWindow();
    }
	
	private void generateCalendar(CalendarView calendarView) {

        Calendar calendar1 = new Calendar("Kalender 1"); 
        Calendar calendar2 = new Calendar("Kalender 2");
        
        calendar1.setStyle(Style.STYLE1); 
        calendar2.setStyle(Style.STYLE2);
        
        calendarView.setEntryDetailsPopOverContentCallback(param -> new BeherenSessieController(param.getEntry()));


        CalendarSource myCalendarSource = new CalendarSource("My Calendars"); 
        myCalendarSource.getCalendars().addAll(calendar1, calendar2);

        calendarView.getCalendarSources().addAll(myCalendarSource); 

        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
                @Override
                public void run() {
                        while (true) {
                                Platform.runLater(() -> {
                                        calendarView.setToday(LocalDate.now());
                                        calendarView.setTime(LocalTime.now());
                                });

                                try {
                                        // update every 10 seconds
                                        sleep(10000);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }

                        }
                };
        };
        
        Entry<String> session1 = new Entry<>("Sessie 1");
        session1.changeStartDate(LocalDate.now().plusDays(1));
        session1.changeStartTime(LocalTime.now().plusHours(5));
        session1.changeStartTime(LocalTime.now().plusHours(10));
        session1.changeEndDate(LocalDate.now().plusDays(1));
        calendar1.addEntry(session1);

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
	}

}
