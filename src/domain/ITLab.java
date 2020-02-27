package domain;

import java.time.LocalDate;
import java.util.List;

public class ITLab {

	//nodig?
	private SessionCalendar currentSessioncalendar;
	private User loggedInUser;
	
	public ITLab() {
		
	}
	//getters and setters 

	private void setCurrentSessioncalendar(SessionCalendar sessionCalendar) {
		this.currentSessioncalendar = sessionCalendar;
	}


	public SessionCalendar getCurrentSessioncalendar() {
		return this.currentSessioncalendar;
	}
	
	public User getLoggedInUser(){
		return this.loggedInUser;
	}

	//methodes
	
	public List<Session> giveSessions() {
		return currentSessioncalendar.getSessions();
	}
	
	public void changeSessionCurrentCalendar(LocalDate startDate, LocalDate endDate) {
		currentSessioncalendar.ChangeDates(startDate,endDate);
		
	}
	
}
