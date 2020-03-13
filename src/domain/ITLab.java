package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;


public class ITLab {

	private SessionCalendar currentSessioncalendar;
	private Session currentSession;
	private User loggedInUser;

	// data
	public final String PERSISTENCE_UNIT_NAME = "ITLab_DB";
	private EntityManager em;
	private EntityManagerFactory emf;

	public ITLab() {
		initializePersistentie();
		//this.setCurrentSessioncalendar(new SessionCalendar(LocalDate.now(), LocalDate.now().plusYears(1))); // TODO
																											// temporary
		try {																								// solution
			setCurrentSessioncalendar(em.createNamedQuery("SessionCal.findCurCal",SessionCalendar.class).setParameter("id", 1).getSingleResult());
		}catch(NoResultException e) {
			System.err.println("Fout in databank");
		}
		
	}

	// methodes

	public List<Session> giveSessions() {
		return currentSessioncalendar.getSessions();
	}

	public void changeSessionCurrentCalendar(LocalDate startDate, LocalDate endDate) {
		currentSessioncalendar.ChangeDates(startDate, endDate);
	}

	public boolean isUserPassComboValid(String username, char[] password) {
		User u;
		try {
			u = em.createQuery(" SELECT u FROM User u WHERE :userName = u.userName ", User.class).setParameter("userName", username).getSingleResult();

			if (u != null && (u.getUserType() == UserType.HEAD || u.getUserType() == UserType.RESPONSIBLE)) {
				if(u.getPassword() != null) {
					if (Arrays.equals(u.getPassword().toCharArray(), password) && username.equals(u.getUserName())) {
						setLoggedInUser(u);
						return true;
					}
				}
			}
		} catch (NoResultException e) {
			System.out.println("No user found with this name");
		}
		return false;
	}

	public void changeSession(int id, String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String description, String nameGuest) {
		Session session = currentSessioncalendar.giveSession(id);
		em.getTransaction().begin();
		session.changeSession(title, classroom, startDate, endDate, maxAttendee, description, nameGuest);
		currentSessioncalendar.addSession(session);
		em.persist(session);
		em.getTransaction().commit();
	}

	
//	public void switchCurrentSession(int sessionID) {
//		setCurrentSession(giveSessions().stream().filter(session -> session.getSessionID() == sessionID).findFirst()
//				.orElse(null));
//	}

//	public boolean doesSessionExist(int sessionID, String title) {
//		// return currentSessioncalendar.getSessions().stream().filter(session -> session.getSessionID() == sessionID && session.getTitle().equals(title)) != null;
//		return false;
//	}

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

	public void setLoggedInUser(User loggedInUser) {
		 this.loggedInUser = loggedInUser;
	}
	
	public boolean isUserHeadOrResponsible() { //head = true responsible = false
		if(loggedInUser.getUserType() == UserType.HEAD)
			return true;
		else return false;
	}
	

	// jpa methodes
	private void initializePersistentie() {
		openPersistentie();
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
