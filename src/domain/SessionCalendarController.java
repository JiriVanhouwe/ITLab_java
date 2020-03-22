package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.NotFoundException;

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
	
	public void changeSessionCalendarByDate(LocalDate startDate) throws NotFoundException {
		itLab.changeSessionCalendaryByDate(startDate);
	}
	
	public void createSessionCalendar(int id, LocalDate startDate, LocalDate endDate) {
		itLab.addSessionCalendar(new SessionCalendar(id,startDate,endDate));
	}
}
