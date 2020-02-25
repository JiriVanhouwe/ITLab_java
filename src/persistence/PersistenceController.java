package persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.SessionCalendar;

public class PersistenceController {

	public final String PERSISTENCE_UNIT_NAME = "ITLab_Database"; 
	    private EntityManager em;
	    private EntityManagerFactory emf;
	    
	    public PersistenceController() {
	    	initializePersistentie();
	    }
	    
	    private void initializePersistentie() {
	        openPersistentie();
	    }

	    private void openPersistentie() {
	        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); 
	        em = emf.createEntityManager();
	    }

	    public void closePersistentie() {
	        em.close();
	        emf.close();
	    }

		public List<SessionCalendar> getSessionCalenders() {
			throw new UnsupportedOperationException();
		}
}
