package domain;

import java.time.LocalDateTime;
import java.util.List;

public class SessionController extends Controller {
	
	public SessionController(ITLab itlab) {
		super(itlab);
	}
	
	public Session giveSession(int sessionID) {
		return this.itLab.getCurrentSession();
	}
	
	public void changeCurrentSession(int sessionID) {
		this.itLab.switchCurrentSession(sessionID);
	}
	
	public List<Session> giveAllSessions(){
		return this.itLab.giveSessions();
	}
	
	public List<Session> giveSessionsCurrentCalendar(){
		throw new UnsupportedOperationException();
	}
	
	public void changeSession(String title, String classRoom, LocalDateTime startDate, LocalDateTime endDate,
			int maxAttendee, String Description, String nameGuest) {
		this.itLab.changeSession(title, classRoom, startDate, endDate, maxAttendee, classRoom, nameGuest);
	}
	
	public void createSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();
	}
	
	public List<Classroom> giveAllClassrooms(){
		throw new UnsupportedOperationException();
	}
}
