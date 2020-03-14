package domain;

import java.time.LocalDateTime;
import java.util.List;

public class SessionController extends Controller {

	public SessionController() {
		super();
	}

	public Session giveSession(String sessionID) {
		for (Session s : itLab.giveSessions()) {
			int id;
			if (sessionID.endsWith("#")) {
				id = Integer.parseInt(sessionID.substring(0, sessionID.length() - 1));
			} else {
				id = Integer.parseInt(sessionID);
			}
			
			if(id == s.getSessionID()) {
				return s;
			}
		}
		return null;
	}

//	public void changeCurrentSession(int sessionID) {
//		this.itLab.switchCurrentSession(sessionID);
//	}

	public List<Session> giveAllSessions() {
		return this.itLab.giveSessions();
	}

	public List<Session> giveSessionsCurrentCalendar() {
		return itLab.giveSessions();
	}

	public String changeSession(String sessionID, String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String description, String nameGuest) {
		if (sessionID.endsWith("#")) {
			int id = Integer.parseInt(sessionID.substring(0, sessionID.length() - 1));
			this.itLab.changeSession(id, title, classroom, startDate, endDate, maxAttendee, description, nameGuest);
			return sessionID;
		} else {
			return this.createSession(title, startDate, endDate, classroom, maxAttendee, description, nameGuest);
		}

	}

	// waarom hier id terug geven -> het entryid moet onmiddelijk aangepast orden
	// naar het juiste sessionid, want een entry neemt anders zijn eigen nummering
	// aan die niet gelijk zal lopen met sessions
	public String createSession(String title, LocalDateTime startDate, LocalDateTime endDate, Classroom classRoom,
			int maxAttendee, String description, String nameGuest) {
		Session session = new Session(title, description, startDate, endDate, maxAttendee, classRoom, nameGuest);
		itLab.addSession(session);
		return String.format("%d#", session.getSessionID());
	}

	public List<Classroom> giveAllClassrooms() {

		return itLab.getEntityManager().createNamedQuery("Classroom.findAll", Classroom.class).getResultList();

	}

	public Classroom giveClassroom(String text) {
		return itLab.getEntityManager().createNamedQuery("Classroom.findById", Classroom.class)
				.setParameter("classid", text).getSingleResult();
	}
}
