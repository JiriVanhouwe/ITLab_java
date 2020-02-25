package domain;

import java.time.LocalDateTime;
import java.util.List;

public class SessionController {
	
	private SessionRepository sessionRepository;
	
	public SessionController(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}
	
	public Session giveSession(int sessionID) {
		throw new UnsupportedOperationException();
	}
	
	public List<Session> giveSessions(){
		throw new UnsupportedOperationException();
	}
	
	public void changeSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();	
	}
	
	public void createSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();	
	}
}
