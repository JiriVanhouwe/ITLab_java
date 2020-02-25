package domain;

import java.time.LocalDateTime;

public class SessionController {
	
	private SessionRepository sessionRepository;
	
	public SessionController(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}
	
	public void changeSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();	
	}
	
	public void createSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();	
	}
}
