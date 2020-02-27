package domain;

import java.time.LocalDateTime;
import java.util.List;

public class SessionController extends Controller {
	
	public SessionController(ITLab itlab) {
		super(itlab);
	}
	
	public Session giveSession(int sessionID) {
		throw new UnsupportedOperationException();
	}
	
	public List<Session> giveAllSessions(){
		throw new UnsupportedOperationException();
	}
	
	public List<Session> giveSessionsCurrentCalendar(){
		throw new UnsupportedOperationException();
	}
	
	public void changeSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();
	}
	
	public void createSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();
	}
}
