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
	


	private SessionCalendar getCurrentSessioncalendar() {
		return currentSessioncalendar;
	}

	private void setCurrentSessioncalendar(SessionCalendar currentSessioncalendar) {
		this.currentSessioncalendar = currentSessioncalendar;
	}

	//methodes
	public Session giveSession(int sessionID){
		throw new UnsupportedOperationException();
	}
	public List<Session> giveSessions() {
		// TODO Auto-generated method stub
		return null;
	}



	public void changeSessionCurrentCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		currentSessioncalendar.ChangeDates(startDate,endDate);
		
	}
	

	
	
	
	
}
