package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessionCalendar {

	private LocalDate startDate;
	private LocalDate endDate;
	private List<Session> sessions;
	private int id;
	
	private static int idCounter = 0;
	

	public SessionCalendar(LocalDate startDate, LocalDate endDate) {
		
		if( startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate))
				throw new IllegalArgumentException("De tijden zijn niet correct");
		
		setStartDate(startDate);
		setEndDate(endDate);
		this.id = idCounter;
		
		idCounter++;
		
		this.sessions = new ArrayList<>();
	}

	//methodes
	public void ChangeDates(LocalDate newStartDate, LocalDate newEndDate) {
		if(this.startDate != newStartDate) {
			setStartDate(newStartDate);
		}
		if(this.endDate != newEndDate) {
			setEndDate(newEndDate);
		}
	}
	
	//setters and getters
	private void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	private void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public List<Session> getSessions() {
		return sessions;
	}
	
	public int getId() {
		return this.id;
	}

	
}
