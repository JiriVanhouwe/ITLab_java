package domain;

import java.time.LocalDate;
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
	
	public void changeSession(String title, LocalDate startDate, LocalDate endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();	
	}
	
	public void createSession(String title, LocalDate startDate, LocalDate endDate,String classRoom ,int maxAttendees , String guestSpeaker ) {
		throw new UnsupportedOperationException();	
	}
}
