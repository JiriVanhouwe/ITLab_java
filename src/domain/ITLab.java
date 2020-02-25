package domain;

import java.util.List;

public class ITLab {

	//nodig?
	private List<User> users;
	private List<SessionCalendar> sessionCalendars;
	private SessionCalendar currentSessioncalendar;
	private User loggedInUser;
	
	public ITLab() {
		getData();
	}
	//getters and setters 
	
	private List<User> getUsers() {
		return users;
	}

	private void setUsers(List<User> users) {
		this.users = users;
	}

	private List<SessionCalendar> getSessionCalendars() {
		return sessionCalendars;
	}

	private void setSessionCalendars(List<SessionCalendar> sessionCalendars) {
		this.sessionCalendars = sessionCalendars;
	}

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
