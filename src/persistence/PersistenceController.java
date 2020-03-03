package persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import domain.Classroom;
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
			Query q = em.createQuery("SELECT c FROM SessionCalendar");
			return (List<SessionCalendar>) q.getResultList();
		}
		
		public List<Session> giveSessions() {
			Query q = em.createQuery("SELECT c FROM Session");
			return (List<Session>) q.getResultList();
	    }

		public Session giveSession(int sessionID) {
			Query q = em.createQuery("SELECT c FROM SessionCalendar WHERE sessionId = " + sessionID);
			return (Session) q.getSingleResult();
		}

		public void updateSessionCalanders(List<SessionCalendar> collect) {
			throw new UnsupportedOperationException();
		}

		public void updateSession(List<Session> collect) {
			throw new UnsupportedOperationException();
		}

		

		public static Classroom giveclassRoom(String classroom) {
			throw new UnsupportedOperationException();
		}
		
		
		
		
}
