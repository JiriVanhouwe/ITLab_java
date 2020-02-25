package domain;

import java.time.LocalDate;
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
	
	public void changeSessionCalendar(LocalDate startDate, LocalDate endDate) {
		ITLabSingleton.getITLabInstance().changeSessionCurrentCalendar(startDate, endDate);
	}
	
	public void createSessionCalendar(LocalDate startDate, LocalDate endDate) {
		sessionCalendarRepository.createSessionCalendar(startDate, endDate);
	}
	
	public void removeSessionCalendar(LocalDate startDate, LocalDate endDate) {
		sessionCalendarRepository.removeSessionCalendar(startDate,endDate);
	}
}
