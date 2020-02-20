package domain;

import java.time.LocalDateTime;
import java.util.List;

public class DomainController {
	private ITlab itlab;
	
	public DomainController() {
		this.itlab = new ITlab();
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
