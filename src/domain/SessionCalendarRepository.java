package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import persistence.PersistenceController;

public class SessionCalendarRepository {
	
	private List<SessionCalendar> sessionCalendars;
	private final PersistenceController persistenceController;
	
	public SessionCalendarRepository(PersistenceController persistenceController) {
		this.persistenceController = persistenceController;
		setSessionCalendars(persistenceController.getSessionCalenders());
	}
	
	//getters and setters
	public List<SessionCalendar> getSessionCalendars() {
		return sessionCalendars;
	}
	public void setSessionCalendars(List<SessionCalendar> sessionCalendars) {
		this.sessionCalendars = sessionCalendars;
	}

	//methodes
	public void removeSessionCalendar(LocalDate startDate, LocalDate endDate) {
		//hier filter stream uit remove halen voor controlep null?
		sessionCalendars.remove(sessionCalendars.stream().filter(e -> e.getStartDate().equals(startDate) && e.getEndDate().equals(endDate)).findFirst().get());
	}
	
	public void createSessionCalendar(LocalDate startDate, LocalDate endDate) {
		sessionCalendars.add(new SessionCalendar(startDate, endDate));
	}

	public void update() {
		persistenceController.updateSessionCalanders(sessionCalendars.stream().sorted(Comparator.comparing(SessionCalendar::getStartDate).thenComparing(SessionCalendar::getEndDate)).collect(Collectors.toList()));
		setSessionCalendars(persistenceController.getSessionCalenders());
	}

	

	
	
	
}
