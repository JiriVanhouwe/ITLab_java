package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SessionCalendar {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private List<Session> sessions;
	

	public SessionCalendar(LocalDateTime startDate, LocalDateTime endDate) {
		setStartDate(startDate);
		setEndDate(endDate);
	}

	//setters and getters
	private void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	private void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
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

	

}
