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
		return sessionCalendarRepository.getSessionsCalendar(year);
	}
	
	public void changeSessionCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		ITLabSingleton.getITLabInstance().changeSessionCurrentCalendar(startDate, endDate);
	}
	
	public void createSessionCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		sessionCalendarRepository.createSessionCalendar(startDate, endDate);
	}
	
	public void  removeSessionCalendar() {
		sessionCalendarRepository.removeSessionCalendar();
	}
}
