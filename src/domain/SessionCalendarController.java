package domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class SessionCalendarController {
	
	private SessionCalendarRepository sessionCalendarRepository;
	
	public SessionCalendarController(SessionCalendarRepository sessionCalendarRepository) {
		this.sessionCalendarRepository = sessionCalendarRepository;
	}
	
	
	//methodes
	public SessionCalendar giveSessionCalendar(){
		return ITLabSingleton.getITLabInstance().getCurrentSessioncalendar();
	}
	
	public List<SessionCalendar> giveSessionCalendars(){
		return Collections.unmodifiableList(sessionCalendarRepository.getSessionCalendars());
	}
	
	public void changeSessionCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		ITLabSingleton.getITLabInstance().changeSessionCurrentCalendar(startDate, endDate);
	}
	
	public void createSessionCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		sessionCalendarRepository.createSessionCalendar(startDate, endDate);
	}
	
	public void removeSessionCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		sessionCalendarRepository.removeSessionCalendar(startDate,endDate);
	}
}
