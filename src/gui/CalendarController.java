package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.controlsfx.control.PopOver;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.LoadEvent;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DayView;

import domain.GuiSession;
import domain.ITLabSingleton;
import domain.RequiredElement;
import domain.Session;
import domain.SessionCalendarController;
import domain.SessionController;
import domain.State;
import domain.UserController;
import domain.UserType;
import exceptions.InformationRequiredException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CalendarController extends HBox {
	private SessionController sessionController;
	private SessionCalendarController sessionCalendarController;
	
	private LocalDate _startDate;
	private LocalDate _endDate;
	private CalendarView _calendarView;
	private Calendar _calendar1;
	
	private boolean entryHasAlreadyBeenDragged = false;
	
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
		sessionCalendarController = new SessionCalendarController();
		
		_startDate = sessionCalendarController.giveSessionCalendar().getStartDate();
		_endDate = sessionCalendarController.giveSessionCalendar().getEndDate();
		
		initializeCalendar();
	}
	
	private void initializeCalendar() {
		_calendarView = new CalendarView();
		_calendarView.setShowAddCalendarButton(false); // make sure it is not possible to add multiple calendars in a calendarview
		_calendarView.setShowPrintButton(false); // make the printing option invisible
		
		_calendar1 = new Calendar("Sessies"); 
		
		_calendarView.addEventHandler(LoadEvent.LOAD, evt -> calendarRangeChanged(evt));
		

        _calendar1.setStyle(Style.STYLE2);

        _calendarView.setEntryDetailsPopOverContentCallback(param -> new ManageSessionController(param.getEntry()));
        
        CalendarSource myCalendarSource = new CalendarSource("Kalender");
        myCalendarSource.getCalendars().add(_calendar1);
        


        _calendarView.getCalendarSources().remove(0);
        _calendarView.getCalendarSources().addAll(myCalendarSource); 

        _calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
                @Override
                public void run() {
                        while (true) {
                                Platform.runLater(() -> {
                                        _calendarView.setToday(LocalDate.now());
                                        _calendarView.setTime(LocalTime.now());
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
        
        linkSessionsToEntries(_calendar1);

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    	
    	HBox.setHgrow(_calendarView, Priority.ALWAYS);
    	
    	this.getChildren().add(_calendarView);
    	
    	EventHandler<CalendarEvent> handler = evt -> calendarEntryChanged(evt);
    	_calendar1.addEventHandler(handler);
	}

	private void linkSessionsToEntries(Calendar calendar) {
    	List<GuiSession> sessions = this.sessionController.giveSessionsCurrentCalendar();
    	
    	loadSessions(sessions);
    }
	
    private void calendarEntryChanged(CalendarEvent evt) {
    	//This method gets called when the start- or endtime of an entry was changed in the calendar by dragging
		GuiSession session = sessionController.giveSession(evt.getEntry().getId());
		if(session == null) {
			return;
		}

    	if(!ITLabSingleton.getITLabInstance().getLoggedInUser().getUserType().equals(UserType.HEAD) &&
    			!ITLabSingleton.getITLabInstance().getLoggedInUser().getUserName().equals(session.getHost().getUserName())) {
    		//If the user doens't have permission we undo the event
			
			if(!this.entryHasAlreadyBeenDragged) {
				entryHasAlreadyBeenDragged = true;
				evt.getEntry().setInterval(evt.getOldInterval());
				
	    		Alert a = new Alert(AlertType.ERROR);
	    		a.setContentText("U heeft geen toestemming om deze sessie te wijzigen");
				a.showAndWait();
			}else {
				entryHasAlreadyBeenDragged = false;
			}
			
			return;
    	}
    	
		if(evt.getEventType().equals(CalendarEvent.ENTRY_INTERVAL_CHANGED)) {
			
			try {
				sessionController.changeSession(session.getSessionID() + "#", session.getTitle(), session.getClassroom(), 
						evt.getEntry().getStartAsLocalDateTime(), evt.getEntry().getEndAsLocalDateTime(), session.getMaxAttendee(),
						session.getDescription(), session.getNameGuest(), session.getMedia(), session.getVideoURL(), session.getStateEnum());
			} catch (IllegalArgumentException e) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText(e.getMessage());
				a.showAndWait();
				evt.getEntry().setInterval(session.getDate().atTime(session.getStartHour()), session.getDate().atTime(session.getEndHour()));
			}catch (InformationRequiredException e) {
				
				Alert a = new Alert(AlertType.ERROR);
				String res = null;
				for(RequiredElement el: e.getInformationRequired()) {
					switch(el) {
					case ATENDEESREQUIRED:
						res += String.format("max aantal aanwezigen niet geldig!%n");
						break;
					case CLASSROOMREQUIRED:
						res += String.format("lokaal niet geldig!%n");
						break;
					case ENDDATEREQUIRED:
						res += String.format("Eind datum niet geldig!%n");
						break;
					case STARTDATEREQUIRED:
						res += String.format("Begin datum niet geldig!%n");
						break;
					case TITLEREQUIRED:
						res += String.format("Title niet geldig!%n");
						break;
							}
						}
				
				a.setContentText(res);
				a.showAndWait();
			}
		}
		//This event gets called when a session/entry is removed
		if(evt.getEventType().equals(CalendarEvent.ENTRY_CALENDAR_CHANGED)) {
			//TODO: Verder uitwerken, dit event wordt niet enkel aangeroepen bij het verwijderen, ook andere aanpassingen
			//er bestaat niet echt een event voor enkel verwijderen
			if(session.getStateEnum().equals(State.CLOSED) || session.getStateEnum().equals(State.FINISHED))
				sessionController.removeSession(evt.getEntry().getId());
			else {
				Entry entry = new Entry();
				entry.setId(Integer.toString(session.getSessionID()) + "#");
				entry.setTitle(session.getTitle());
        		entry.setInterval(session.getDate().atTime(session.getStartHour()), session.getDate().atTime(session.getEndHour()));
        		_calendar1.addEntry(entry);
			}
		}
	}
    
    private void calendarRangeChanged(LoadEvent evt) {
    	LocalDate newStartDate = evt.getStartDate();
    	LocalDate newEndDate = evt.getEndDate();
    	List<GuiSession> sessions = new ArrayList<>();
    	
    	if (newStartDate.isBefore(_startDate) || newEndDate.isAfter(_endDate)) {
    		_startDate = newStartDate;
    		_endDate = newEndDate;
    		
    		sessions = sessionController.giveSessionsBetweenDates(_startDate, _endDate);
    		loadSessions(sessions);
    	}
    }
    
    private void loadSessions(List<GuiSession> sessions) {
    	sessions.stream().forEach(session -> {
    		Entry entry = new Entry();
    		entry.setId(Integer.toString(session.getSessionID()) + "#");
    		session.updateState();
    		if(_calendar1.findEntries(entry.getId()).isEmpty()) {
        		entry.setTitle(session.getTitle());
        		entry.setInterval(session.getDate().atTime(session.getStartHour()), session.getDate().atTime(session.getEndHour()));
        		_calendar1.addEntry(entry);
    		}
    	});
    }
}
