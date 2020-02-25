package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessionCalendar {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private List<Session> sessions;
	

	public SessionCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		
		if( startDate.isBefore(LocalDateTime.now()) || endDate.isBefore(startDate))
				throw new IllegalArgumentException("De tijden zijn niet correct");
		
		setStartDate(startDate);
		setEndDate(endDate);
		
		this.sessions = new ArrayList<>();
	}

	//methodes
	public void ChangeDates(LocalDateTime newStartDate, LocalDateTime newEndDate) {
		if(this.startDate != newStartDate) {
			setStartDate(newStartDate);
		}
		if(this.endDate != newEndDate) {
			setEndDate(newEndDate);
		}
	}
	
	//setters and getters
	private void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	private void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	
}
