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
		return itLab.giveSessions();
	}
	
	public int changeSession(int sessionID, String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String description, String nameGuest) {
		if(itLab.doesSessionExist(sessionID, title)) {
			this.itLab.changeSession(title, classroom, startDate, endDate, maxAttendee, description, nameGuest);
			return sessionID;
		} else {
			return this.createSession(title, startDate, endDate, classroom, maxAttendee, description, nameGuest);
		}
		
	}
	// waarom hier id terug geven
	public int createSession(String title, LocalDateTime startDate, LocalDateTime endDate, Classroom classRoom, int maxAttendee, String description, String nameGuest ) {
		Session session = new Session(title, description, startDate, endDate, maxAttendee, classRoom,  nameGuest);
		return session.getSessionID();
	}
	
	public List<Classroom> giveAllClassrooms(){
		
		return itLab.getEntityManager().createNamedQuery("Classroom.findAll",Classroom.class).getResultList();

	}

	public Classroom giveClassroom(String text) {
		return itLab.getEntityManager().createNamedQuery("Classroom.findById",Classroom.class).setParameter("classid", text).getSingleResult();
	} 
}
