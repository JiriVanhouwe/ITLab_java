package domain;

import java.time.LocalDateTime;
import java.util.List;

public class SessionController extends Controller {
	
	public SessionController() {
		super();
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
	
	public int changeSession(int sessionID, String title, String classRoom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String description, String nameGuest) {
		if(itLab.doesSessionExist(sessionID, title)) {
			this.itLab.changeSession(title, classRoom, startDate, endDate, maxAttendee, classRoom, nameGuest);
			return sessionID;
		} else {
			return this.createSession(title, startDate, endDate, classRoom, maxAttendee, description, nameGuest);
		}
		
	}
	
	public int createSession(String title, LocalDateTime startDate, LocalDateTime endDate, String classRoom, int maxAttendee, String description, String nameGuest ) {
		Session session = new Session(title, new Classroom(classRoom, Campus.GENT, 30, ClassRoomCategory.ITLAB), startDate, endDate, maxAttendee, description, nameGuest);
		itLab.addSession(session);
		return session.getSessionID();
	}
	
	public List<Classroom> giveAllClassrooms(){
		return itLab.getEntityManager().createNamedQuery("Classroom.findAll",Classroom.class).getResultList();

	}
}
