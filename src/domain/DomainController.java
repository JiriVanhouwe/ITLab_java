package domain;

import java.time.LocalDateTime;
import java.util.List;

import persistence.PersistenceController;

public class DomainController {
	private ITLab itlab;
	private PersistenceController persistenceController;
	
	public DomainController(PersistenceController pc) {
		this.itlab = new ITLab();
		this.persistenceController = pc;
	}
	
	//methodes
	public List<Session> giveSessions(){
		return itlab.giveSessions();
	}
	
	public Session giveSessions(int sessionID){
		return itlab.giveSession(sessionID);
	}
	
	public void changeSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();	
	}
	
	public void createSession(String title, LocalDateTime startDate, LocalDateTime endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();	
	}
}
