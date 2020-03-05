package domain;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ITLab {

	private SessionCalendar currentSessioncalendar;
	private Session currentSession;
	private User loggedInUser;
	private List<User> users;

	// data
	public final String PERSISTENCE_UNIT_NAME = "ITLab_DB";
	private EntityManager em;
	private EntityManagerFactory emf;

	public ITLab() {
		initializePersistentie();
		this.setCurrentSessioncalendar(new SessionCalendar(LocalDate.now(), LocalDate.now().plusYears(1))); // TODO
																											// temporary
																											// solution
		loadUsers();
	}
	

	// methodes

	public List<Session> giveSessions() {
		return currentSessioncalendar.getSessions();
	}

	public void changeSessionCurrentCalendar(LocalDate startDate, LocalDate endDate) {
		currentSessioncalendar.ChangeDates(startDate, endDate);
	}

	public boolean isUserPassComboValid(String username, char[] password) {
		for(User u : users) {
			if(Arrays.equals(u.getPassword().toCharArray(), password)) {
				return true;
			}
		}
		return false;
	}

	public void changeSession(String title, String classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String description, String nameGuest) {
		currentSession.changeSession(title, description, startDate, endDate, maxAttendee, giveClassRoom(classroom), nameGuest);

	}

	private Classroom giveClassRoom(String classroom) {
		return persistence.PersistenceController.giveclassRoom(classroom);
	}

	public void switchCurrentSession(int sessionID) {
		setCurrentSession(giveSessions().stream().filter(session -> session.getSessionID() == sessionID).findFirst().orElse(null));
	}

	public boolean doesSessionExist(int sessionID, String title) {
		// return currentSessioncalendar.getSessions().stream().filter(session -> session.getSessionID() == sessionID && session.getTitle().equals(title)) != null;
		return false;
	}

	// data methodes
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

	private void loadUsers() {
		this.users = em.createQuery("User.getAllUsers", User.class).getResultList();
	}

	public void populateData() {
		SessionCalendar sessionCalendar = new SessionCalendar(LocalDate.now(), LocalDate.now().plusDays(60));
		addSessionCalendar(sessionCalendar);
		this.currentSessioncalendar = sessionCalendar;
		Classroom cr = new Classroom("ITLAB", Campus.GENT, 30, ClassRoomCategory.ITLAB);

		Session session = new Session("Api's met Jiri", cr, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(1), 5);
		Session session2 = new Session("Databasen met Arend", cr, LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(1), 5);

		addSession(session2);
		addSession(session);

		System.out.println("session: " + session.getSessionID());
		System.out.println("session2: " + session2.getSessionID());
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


	public EntityManager getEntityManager() {
		return em;
	}

	private EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	public User getLoggedInUser() {
		return this.loggedInUser;
	}

	public User setLoggedInUser(User loggedInUser) {
		return this.loggedInUser = loggedInUser;
	}

	public List<User> getUsers() {
		return users;
	}
	
	public User getUserByUsername(String userName) {
		return em.createQuery("User.getUserByUserName", User.class).getSingleResult();
	}


	// jpa methodes
	private void initializePersistentie() {
		openPersistentie();
		// PersistenceController persistenceController = new PersistenceController(this);
		// populateData();
	}

	private void openPersistentie() {
		this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		this.em = emf.createEntityManager();
	}

	public void closePersistentie() {
		em.close();
		emf.close();
	}

}
