package domain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SessionCalendarController extends Controller {

	public SessionCalendarController() {
		super();
	}
	
	//methodes
	public SessionCalendar giveSessionCalendar(){
		return super.itLab.getCurrentSessioncalendar();
	}
	
	public List<Session> giveSessionsCurrentMonth() {
		return itLab.getCurrentSessioncalendar()
					.getSessions()
					.stream()
					.filter(session -> session.getDate().getMonth() == LocalDate.now().getMonth())
					.collect(Collectors.toList());
	}
	
	public List<SessionCalendar> giveSessionCalendars(){
		return itLab.getSessionCalendars();
	}
	
	public void changeSessionCalendar(LocalDate startDate, LocalDate endDate) {
		throw new UnsupportedOperationException();
	}
	
	public void createSessionCalendar(int id, LocalDate startDate, LocalDate endDate) {
		itLab.addSessionCalendar(new SessionCalendar(id,startDate,endDate));
	}
	
	public void removeSessionCalendar(LocalDate startDate, LocalDate endDate) {
		throw new UnsupportedOperationException();
	}
}
