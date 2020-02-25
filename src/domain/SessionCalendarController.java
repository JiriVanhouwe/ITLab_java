package domain;

import java.time.LocalDateTime;
import java.util.List;


public class SessionCalendarController {
	
	private SessionCalendarRepository sessionCalendarRepository;
	
	public SessionCalendarController(SessionCalendarRepository sessionCalendarRepository) {
		this.sessionCalendarRepository = sessionCalendarRepository;
	}
	
	
	//methodes

	public SessionCalendar giveSessionCalendar(int year){
		return sessionCalendarRepository.getSessionsCalendars();
	}
	
	public void changeSessionCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		sessionCalendarRepository.changeSessionCalendar(startDate, endDate);
	}
	
	public void createSessionCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		throw new UnsupportedOperationException();
	}
	
	public void  removeSessionCalendar() {
		throw new UnsupportedOperationException();
	}
}
