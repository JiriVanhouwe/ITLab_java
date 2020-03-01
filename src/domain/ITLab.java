package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import persistence.PersistenceController;

public class ITLab {

	private SessionCalendar currentSessioncalendar;
	private Session currentSession;
	private User loggedInUser;
	public final String PERSISTENCE_UNIT_NAME = "ITLab_DB";
	private EntityManager em;
	private EntityManagerFactory emf;

	public ITLab() {
		//initializePersistentie();
	}

	public User setLoggedInUser(User loggedInUser) {
		return this.loggedInUser = loggedInUser;
	}

	// getters and setters
	
	
	private void setCurrentSessioncalendar(SessionCalendar sessionCalendar) {
		this.currentSessioncalendar = sessionCalendar;
	}
	
	public Session getCurrentSession() {
		return this.currentSession;
	}
	
	private void setCurrentSession(Session session) {
		this.currentSession = session;
	}

	public SessionCalendar getCurrentSessioncalendar() {
		return this.currentSessioncalendar;
	}

	public User getLoggedInUser() {
		return this.loggedInUser;
	}

	// jpa methodes
	private void initializePersistentie() {
		openPersistentie();
		PersistenceController persistenceController = new PersistenceController(this);
		persistenceController.populateData();
	}

	private void openPersistentie() {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = emf.createEntityManager();
	}

	public void closePersistentie() {
		em.close();
		emf.close();
	}

	// methodes

	public List<Session> giveSessions() {
		return currentSessioncalendar.getSessions();
	}

	public void changeSessionCurrentCalendar(LocalDate startDate, LocalDate endDate) {
		currentSessioncalendar.ChangeDates(startDate, endDate);
	}

	public boolean isUserPassComboValid(String username, char[] password) {

		return true;
	}
	
	public void changeSession(String title, String classroom, LocalDateTime startDate, LocalDateTime endDate,
			int maxAttendee, String description, String nameGuest){
		currentSession.changeSession(title, description, startDate, endDate, maxAttendee, giveClassRoom(classroom), nameGuest);
		
	}
	
	private Classroom giveClassRoom(String classroom) {
		return persistence.PersistenceController.giveclassRoom(classroom);
	}

	public void switchCurrentSession(int sessionID) {
		setCurrentSession(giveSessions().stream().filter(session -> session.getSessionID() == sessionID).findFirst().orElse(null));
	}
}
