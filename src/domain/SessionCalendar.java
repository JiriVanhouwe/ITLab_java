package domain;

import java.time.LocalDate;
import java.util.List;

public class SessionCalendar {

	private LocalDate startDate;
	private LocalDate endDate;
	private List<Session> sessions;
	
	public SessionCalendar(LocalDate startDate, LocalDate endDate) {
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}
	
	
}
