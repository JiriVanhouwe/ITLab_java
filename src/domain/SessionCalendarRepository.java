package domain;

import java.time.LocalDateTime;
import java.util.List;
import persistence.PersistenceController;

public class SessionCalendarRepository {
	
	private List<SessionCalendar> sessionCalendars;
	private final PersistenceController persistenceController;
	
	public SessionCalendarRepository() {
		this.persistenceController = new PersistenceController();
		this.sessionCalendars = persistenceController.getSessionCalenders();
	}
	
	//getters and setters
	

	public SessionCalendar getSessionsCalendar(int year) {
		
		throw new UnsupportedOperationException();
	}

	
	public void createSessionCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		sessionCalendars.add(new SessionCalendar(startDate, endDate));
	}

	public void removeSessionCalendar() {
		// TODO Auto-generated method stub
		
	}
	
	
}
