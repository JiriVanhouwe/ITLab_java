package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ITLab {

	private SessionCalendar currentSessioncalendar;
	private Session currentSession;
	private User loggedInUser;
	
	//data 
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
		//PersistenceController persistenceController = new PersistenceController(this);
		populateData();
	}

	private void openPersistentie() {
		this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		this.em = emf.createEntityManager();
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
	
	//data methodes
			public void addSessionCalendar(SessionCalendar sessionCalendar) {
				em.getTransaction().begin();
				em.persist(sessionCalendar);
				em.getTransaction().commit();
			}
			
			public void addSession(Session session) {
				em.getTransaction().begin();
				currentSessioncalendar.addSession(session);
				em.persist(session);
				em.getTransaction().commit();
			}
			
			public void populateData() {
				SessionCalendar sessionCalendar = new SessionCalendar(LocalDate.now(),LocalDate.now().plusDays(60));
				addSessionCalendar(sessionCalendar);
				this.currentSessioncalendar = sessionCalendar;
				Classroom cr = new Classroom("ITLAB", Campus.GENT, 30, ClassRoomCategory.ITLAB);
				
				Session session = new Session("title", cr, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(1), 5);
				Session session2 = new Session("title", cr, LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(1), 5);
				
				addSession(session2);
				addSession(session);
			}

}
