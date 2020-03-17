package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import domain.GuiSession;
import domain.RequiredElement;
import domain.Session;
import domain.SessionController;
import exceptions.InformationRequiredException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CalendarController extends HBox {
	private SessionController sessionController;
	
	public CalendarController() {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Calendar.fxml"));
    	loader.setController(this);
    	loader.setRoot(this);
    	
    	try{
    	      loader.load();
    	} 
    	catch (IOException ex){
    	      throw new RuntimeException(ex);
    	}  
		
		sessionController = new SessionController();
		initializeCalendar();
	}
	
	private void initializeCalendar() {
		CalendarView calendarView = new CalendarView();
		calendarView.setShowAddCalendarButton(false); // make sure it is not possible to add multiple calendars in a calendarview
		calendarView.setShowPrintButton(false); // make the printing option invisible
		
		Calendar calendar1 = new Calendar("Sessies"); 
		
		

        calendar1.setStyle(Style.STYLE2);

        calendarView.setEntryDetailsPopOverContentCallback(param -> new BeherenSessieController(param.getEntry()));
        

        CalendarSource myCalendarSource = new CalendarSource("Kalender");
        myCalendarSource.getCalendars().add(calendar1);
        


        calendarView.getCalendarSources().remove(0);
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
        
        linkSessionsToEntries(calendar1);

        Entry<String> session1 = new Entry<>("Sessie 1");
        session1.changeStartDate(LocalDate.now().plusDays(1));
        session1.changeStartTime(LocalTime.now().plusHours(1));
        session1.changeEndTime(LocalTime.now().plusHours(6));
        session1.changeEndDate(LocalDate.now().plusDays(1));
        calendar1.addEntry(session1);

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    	
    	HBox.setHgrow(calendarView, Priority.ALWAYS);
    	
    	this.getChildren().add(calendarView);
    	
    	EventHandler<CalendarEvent> handler = evt -> calendarEntryChanged(evt);
    	calendar1.addEventHandler(handler);
	}

	private void linkSessionsToEntries(Calendar calendar) {
    	List<GuiSession> sessions = this.sessionController.giveSessionsCurrentCalendar();
    	
    	sessions.stream().forEach(session -> {
    		Entry entry = new Entry();
    		entry.setId(Integer.toString(session.getSessionID()) + "#");
    		entry.setTitle(session.getTitle());
    		entry.setInterval(session.getDate().atTime(session.getStartHour()), session.getDate().atTime(session.getEndHour()));
    		calendar.addEntry(entry);
    	});
    }
	
    private void calendarEntryChanged(CalendarEvent evt) {
		if(evt.getEventType().equals(CalendarEvent.ENTRY_INTERVAL_CHANGED)) {
			Session session = sessionController.giveSession(evt.getEntry().getId());
			System.out.println(session.getSessionID());
			try {
				sessionController.changeSession(session.getSessionID() + "#", session.getTitle(), session.getClassroom(), evt.getEntry().getStartAsLocalDateTime(), evt.getEntry().getEndAsLocalDateTime(), session.getMaxAttendee(), session.getDescription(), session.getNameGuest(), session.getMedia());
			} catch (InformationRequiredException e) {
				// TODO Auto-generated catch block
				Alert a = new Alert(AlertType.ERROR);
				String res = null;
				for(RequiredElement el: e.getInformationRequired()) {
					switch(el) {
					case ATENDEESREQUIRED:
						res += String.format("Fout: bij instellen max aanwezigen%n");
						break;
					case CLASSROOMREQUIRED:
						res += String.format("Fout: bij instellen klas lokaal");
						break;
					case ENDDATEREQUIRED:
						res += String.format("Fout: bij instellen eind datum");
						break;
					case STARTDATEREQUIRED:
						res += String.format("Fout: bij instellen start datum");
						break;
					case TITLEREQUIRED:
						res += String.format("Fout: bij instellen van de title");
						break;
							}
						}
				
				a.setContentText(res);
			}
		}
	}
}
