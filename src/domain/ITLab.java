package domain;

import java.util.List;

public class ITLab {

	//nodig?
	private SessionCalendar currentSessioncalendar;
	private User loggedInUser;
	
	public ITLab() {
		getData();
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
	
	private void getData() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	
	
	
	
}
