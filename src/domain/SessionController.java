package domain;

import java.time.LocalDateTime;
import java.util.List;

public class SessionController extends Controller {
	
	public SessionController(ITLab itlab) {
		super(itlab);
	}
	
	public Session giveSession(int sessionID) {
		return this.itLab.getSession();
	}
	
	public List<Session> giveAllSessions(){
		return this.itLab.giveSessions();
	}
	
	public List<Session> giveSessionsCurrentCalendar(){
		throw new UnsupportedOperationException();
	}
	
	public void changeSession(String title, String description, LocalDateTime startDate, LocalDateTime endDate,
			int maxAttendee, String classRoom, String nameGuest) {
		this.itLab.changeSession(title, description, startDate, endDate, maxAttendee, classRoom, nameGuest);
	}
	
	public void createSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();
	}
}
