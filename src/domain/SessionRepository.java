package domain;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import persistence.PersistenceController;

public class SessionRepository {

	private List<Session> sessions;
	private final PersistenceController persistenceController;
	
	public SessionRepository(PersistenceController persistenceController) {
		this.persistenceController = persistenceController;
		setSessions(persistenceController.giveSessions());
	}
	
	private void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public List<Session> giveSessions(){
		return persistenceController.giveSessions();
	}
	
	public Session giveSession(int sessionID) {
		return persistenceController.giveSession(sessionID);
	}
	
	public void changeSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		//
		throw new UnsupportedOperationException();
	}
	
	public void createSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees ) {
		Session session = new Session(title, classRoom, startDate, endDate, maxAttendees);
		sessions.add(session);	
	}

	public Session getSessionByID(int sessionID) {
		return sessions.stream().filter(e -> e.getSessionID() == sessionID).findAny().get();
	}

	public List<Session> getSessions() {
		return sessions;
	}
	
	public void update() {
		persistenceController.updateSession(sessions.stream().sorted(Comparator.comparing(Session::getStartDate).thenComparing(Session::getEndDate)).collect(Collectors.toList()));
		setSessions(persistenceController.giveSessions());
	}

}
