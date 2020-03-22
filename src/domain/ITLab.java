package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import exceptions.NotFoundException;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.image.Image;


public class ITLab {

	private SessionCalendar currentSessioncalendar;
	private Session currentSession;
	private User loggedInUser;
	private List<Classroom> classrooms;
	private ObservableList<User> users;
	private List<SessionCalendar> sessionCalendars;

	// data
	public final String PERSISTENCE_UNIT_NAME = "ITLab_DB";
	private EntityManager em;
	private EntityManagerFactory emf;

	public ITLab() {
		initializePersistentie();
		
		try {																								
			setCurrentSessioncalendar(em.createNamedQuery("SessionCal.findCurCal",SessionCalendar.class).setParameter("id", 20192020 ).getSingleResult());
			sessionCalendars = em.createNamedQuery("SessoinCal.findAll", SessionCalendar.class).getResultList();
			loadClassrooms();
			
			loadAllUsers();
			if(users == null)
				this.users = FXCollections.observableList(new ArrayList<>());
			
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
	
	public List<Session> giveSessionsBetweenDates(LocalDate startDate, LocalDate endDate) {
		return sessionCalendars.stream()
								.filter(sc -> !(sc.getStartDate().isBefore(startDate) || sc.getEndDate().isAfter(endDate)))
								.flatMap(sc -> sc.getSessions().stream())
								.collect(Collectors.toList());
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

	public void changeSession(int id, String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String description, String nameGuest, List<Integer> media, String videoURL) {
		Session session = currentSessioncalendar.giveSession(id);
		em.getTransaction().begin();
		session.changeSession(title, classroom, startDate, endDate, maxAttendee, description, nameGuest, media, videoURL, loggedInUser);
		currentSessioncalendar.addSession(session);
		em.persist(session);
		em.getTransaction().commit();
	}
	
	public void changeSessionCalendaryByDate(LocalDate startDate) throws NotFoundException {
		SessionCalendar sessionCalendar = sessionCalendars.stream()
															.filter(sc -> !(startDate.isBefore(sc.getStartDate()) || startDate.isAfter(sc.getEndDate())))
															.findFirst()
															.orElse(null);
		
		if(sessionCalendar == null) 
			throw new NotFoundException();
			
		this.currentSessioncalendar = sessionCalendar;
		
	}

	private void loadClassrooms() {
		this.classrooms = em.createNamedQuery("Classroom.findAll", Classroom.class).getResultList();
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
	
	
	// getters and setters
	public List<SessionCalendar> getSessionCalendars() {
		return this.sessionCalendars;
	}
	
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
		users = FXCollections.observableArrayList(getEntityManager().createNamedQuery("User.getAllUsers", User.class).getResultList());
	}
	
	public List<User> getAllUsers(){
		return users;
	}
	
	private User giveUser(String username) {
		for(User u : users) {
			if(u.getUserName().equals(username)) {
				return u;
			}
		}
		return null;
	}
	
	public void removeUser(String username) {
		this.users.remove(giveUser(username));
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}

	public void setLoggedInUser(User loggedInUser) {
		 this.loggedInUser = loggedInUser;
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
