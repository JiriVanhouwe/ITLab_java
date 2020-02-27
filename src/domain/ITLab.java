package domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import persistence.PersistenceController;

public class ITLab {

	
	private SessionCalendar currentSessioncalendar;
	private User loggedInUser;
	public final String PERSISTENCE_UNIT_NAME = "naamdb";
	private EntityManager em;
	private EntityManagerFactory emf;
	
	public ITLab() {
		initializePersistentie();
	}
	
	public User setLoggedInUser(User loggedInUser){
		return this.loggedInUser = loggedInUser;
	}
	
	//getters and setters
	private void setCurrentSessioncalendar(SessionCalendar sessionCalendar) {
		this.currentSessioncalendar = sessionCalendar;
	}
	

	public SessionCalendar getCurrentSessioncalendar() {
		return this.currentSessioncalendar;
	}
	
	public User getLoggedInUser(){
		return this.loggedInUser;
	}
	
	//jpa methodes 
	private void initializePersistentie() {
        openPersistentie();
       PersistenceController persistenceController = new PersistenceController(this);
        persistenceController.populeerData();
    }
	
	 private void openPersistentie() {
	        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	        em = emf.createEntityManager();
	    }

	    public void closePersistentie() {
	        em.close();
	        emf.close();
	    }
	
	//methodes
	
	public List<Session> giveSessions() {
		return currentSessioncalendar.getSessions();
	}
	
	public void changeSessionCurrentCalendar(LocalDate startDate, LocalDate endDate) {
		currentSessioncalendar.ChangeDates(startDate,endDate);
		
	}

	public boolean isUserPassComboValid(String username, char[] password) {
		
		return true;
	}
	
}
