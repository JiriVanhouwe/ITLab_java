package domain;

import java.time.LocalDateTime;
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
		return currentSessioncalendar;
	}

	//methodes
	public Session giveSession(int sessionID){
		throw new UnsupportedOperationException();
	}
	public List<Session> giveSessions() {
		return currentSessioncalendar.getSessions();
	}
	
	public void changeSessionCurrentCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		currentSessioncalendar.ChangeDates(startDate,endDate);
		
	}
	
}
