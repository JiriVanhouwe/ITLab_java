package persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.ITLab;
import domain.Session;
import domain.SessionCalendar;

public class PersistenceController {

	//ik denk dat deze jpa delen in de ITLAB zoals vb garagebeheerder
	public final String PERSISTENCE_UNIT_NAME = "ITLab_Database"; 
	    private EntityManager em;
	    private EntityManagerFactory emf;
	    
	    private ITLab itLab;
	    
	    public PersistenceController(ITLab itLab) {
	    	this.itLab = itLab;
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
		
		public List<Session> giveSessions() {
	    	throw new UnsupportedOperationException();
	    }

		public Session giveSession(int sessionID) {
			throw new UnsupportedOperationException();
		}

		public void updateSessionCalanders(List<SessionCalendar> collect) {
			throw new UnsupportedOperationException();
			
		}

		public void updateSession(List<Session> collect) {
			throw new UnsupportedOperationException();
		}

		public void populeerData() {
			throw new UnsupportedOperationException();
		}
		

		
}
