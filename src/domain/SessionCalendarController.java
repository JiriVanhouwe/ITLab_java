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
	public GuiSessionCalendar giveSessionCalendar(){
		return super.itLab.getSessionCalendars()
							.stream()
							.map(sc -> (GuiSessionCalendar)sc)
							.findFirst()
							.orElse(null);
	}
	
	public GuiSessionCalendar giveSessionCalendarById(int id){
		return super.itLab.getSessionCalendars()
							.stream()
							.filter(sc -> sc.getId() == id)
							.map(sc -> (GuiSessionCalendar)sc)
							.findFirst()
							.orElse(null);
	}
	
	public List<Session> giveSessionsCurrentMonth() {
		return itLab.getCurrentSessioncalendar()
					.getSessions()
					.stream()
					.filter(session -> session.getDate().getMonth() == LocalDate.now().getMonth())
					.collect(Collectors.toList());
	}
	
	public List<GuiSessionCalendar> giveSessionCalendars(){
		return itLab.getSessionCalendars()
					.stream()
					.map(sc -> (GuiSessionCalendar)sc)
					.collect(Collectors.toList());
	}
	
	public void changeSessionCalendarByDate(LocalDate startDate) throws NotFoundException {
		itLab.changeSessionCalendaryByDate(startDate);
	}
	
	public void createSessionCalendar(int id, LocalDate startDate, LocalDate endDate) {
		itLab.addSessionCalendar(new SessionCalendar(id,startDate,endDate));
	}
	
	public void editSessionCalendar(int id, LocalDate startDate, LocalDate endDate) {
		itLab.editSessionCalendar(id, startDate, endDate);
	}
	
	public boolean doesSessionCalendarExist(int id) {
		return itLab.doesSessionCalendarExist(id);
	}
}
