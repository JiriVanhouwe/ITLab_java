package domain;

import java.time.LocalDateTime;
import java.util.List;

public class SessionController {
	
	private SessionRepository sessionRepository;
	
	public SessionController(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}
	
	public Session giveSession(int sessionID) {
		return sessionRepository.getSessionByID(sessionID);
	}
	
	public List<Session> giveAllSessions(){
		return sessionRepository.giveSessions();
	}
	
	public List<Session> giveSessionsCurrentCalendar(){
		return ITLabSingleton.getITLabInstance().giveSessions();
	}
	
	public void changeSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		sessionRepository.changeSession(title, startDate, endDate, classRoom, maxAttendees, guestSpeaker);	
		sessionRepository.update();
	}
	
	public void createSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		sessionRepository.createSession(title, startDate, endDate, classRoom, maxAttendees);	
		sessionRepository.update();
	}
}
