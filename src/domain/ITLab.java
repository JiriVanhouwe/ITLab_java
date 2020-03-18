package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.image.Image;


public class ITLab {

	private SessionCalendar currentSessioncalendar;
	private Session currentSession;
	private User loggedInUser;
	private List<Classroom> classrooms;
	private ObservableList<User> allUsers;
	private FilteredList<User> filteredUserList;

	// data
	public final String PERSISTENCE_UNIT_NAME = "ITLab_DB";
	private EntityManager em;
	private EntityManagerFactory emf;

	public ITLab() {
		initializePersistentie();
		
		try {																								
			setCurrentSessioncalendar(em.createNamedQuery("SessionCal.findCurCal",SessionCalendar.class).setParameter("id", 20192020 ).getSingleResult());
			loadClassrooms();
			loadAllUsers();
			filteredUserList = new FilteredList<>(allUsers, u -> true);
			
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

	public boolean isUserPassComboValid(String username, String password) {
		User u;
		try {
			u = em.createQuery(" SELECT u FROM User u WHERE :userName = u.userName ", User.class).setParameter("userName", username).getSingleResult();

			if (u != null && (u.getUserType() == UserType.HEAD || u.getUserType() == UserType.RESPONSIBLE)) {
				if(u.getPassword() != null) {
					if (u.getPassword().equals(password) && username.equals(u.getUserName())) {
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

	public void changeSession(int id, String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String description, String nameGuest, List<Integer> media) {
		Session session = currentSessioncalendar.giveSession(id);
		em.getTransaction().begin();
		session.changeSession(title, classroom, startDate, endDate, maxAttendee, description, nameGuest, media);
		currentSessioncalendar.addSession(session);
		em.persist(session);
		em.getTransaction().commit();
	}

	private void loadClassrooms() {
		this.classrooms = em.createNamedQuery("Classroom.findAll", Classroom.class).getResultList();
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

	//begin: alles met users
	public User getLoggedInUser() {
		return this.loggedInUser;
	}
	
	public void loadAllUsers() {
		allUsers = FXCollections.observableArrayList(getEntityManager().createNamedQuery("User.getAllUsers", User.class).getResultList());
	}
	
	public ObservableList<User> getAllUsers(){
		return filteredUserList;
	}

	public void setLoggedInUser(User loggedInUser) {
		 this.loggedInUser = loggedInUser;
	}

	public boolean isUserHeadOrResponsible() { //head = true responsible = false
		if(loggedInUser.getUserType() == UserType.HEAD)
			return true;
		else return false;
	}
	
	public void changeFilter(String filter) {
		filteredUserList.setPredicate(user -> 
		{
			if(filter == null || filter.isBlank())
				return true;
			
			String lowerCaseFilter = filter.toLowerCase();
			return user.getFirstName().toLowerCase().contains(lowerCaseFilter) 
					|| user.getLastName().toLowerCase().contains(lowerCaseFilter)
					|| user.getUserName().toLowerCase().contains(lowerCaseFilter)
					|| user.giveUserStatus().toLowerCase().contains(lowerCaseFilter)
					|| user.giveUserType().toLowerCase().contains(lowerCaseFilter);
		});
	}
	//eind: alles met users
	
	public List<Classroom> getClassrooms() {
		return classrooms;
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
