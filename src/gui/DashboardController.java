package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.CalendarView;

import domain.User;
import domain.UserController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class DashboardController extends AnchorPane{ 
	
    @FXML
    private ImageView img_calendar;
    
    @FXML
    private HBox hbox_mainSection;

    private UserController usercontroller;
    
    public DashboardController(UserController usercontroller) {
    	this.usercontroller = usercontroller;
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
    	loader.setController(this);
    	loader.setRoot(this);
    	
    	try{
    	      loader.load();
    	} 
    	catch (IOException ex){
    	      throw new RuntimeException(ex);
    	}  	
    	
    	initializeScreen();
    	initializeCalendar();

    }
    
    private void initializeCalendar() {
		CalendarView calendarView = new CalendarView();
		
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
        session1.changeStartTime(LocalTime.now().plusHours(1));
        session1.changeEndTime(LocalTime.now().plusHours(6));
        session1.changeEndDate(LocalDate.now().plusDays(1));
        calendar1.addEntry(session1);

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
        

    	
    	changeMainSection(calendarView);
	}
    
    private void changeMainSection(Node node) {
    	if(hbox_mainSection.getChildren().size() > 0)
    		hbox_mainSection.getChildren().remove(0);
    	
    	HBox.setHgrow(node, Priority.ALWAYS);
    	hbox_mainSection.getChildren().add(node);
    }

	private void initializeScreen() {
		User user = usercontroller.giveLoggedInUser();
		//name_txt.setText(user.getFirstName() + " " + user.getLastName());
	}
  

}