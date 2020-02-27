package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class SessionCalendarController {

	public SessionCalendarController(ITLab itLab) {
		super();
	}
	
	//methodes
	public SessionCalendar giveSessionCalendar(){
		throw new UnsupportedOperationException();
	}
	
	public List<SessionCalendar> giveSessionCalendars(){
		throw new UnsupportedOperationException();
	}
	
	public void changeSessionCalendar(LocalDate startDate, LocalDate endDate) {
		throw new UnsupportedOperationException();
	}
	
	public void createSessionCalendar(LocalDate startDate, LocalDate endDate) {
		throw new UnsupportedOperationException();
	}
	
	public void removeSessionCalendar(LocalDate startDate, LocalDate endDate) {
		throw new UnsupportedOperationException();
	}
}
