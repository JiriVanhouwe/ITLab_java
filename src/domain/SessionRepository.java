package domain;

import java.time.LocalDateTime;
import java.util.List;

public class SessionRepository {

	private List<Session> sessions;
	
	public SessionRepository() {
		//fill sessions
	}
	
	public List<Session> giveSessions(){
		throw new UnsupportedOperationException();
	}
	
	public Session giveSession(int sessionID) {
		throw new UnsupportedOperationException();
	}
	
	public void changeSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();	
	}
	
	public void createSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();	
	}
	
	
}
