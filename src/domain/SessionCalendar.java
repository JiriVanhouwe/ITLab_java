package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name = "SessionCal.findCurCal", 
			query = "Select sc from SessionCalendar sc"
					+ " where sc.id = :id")
})
public class SessionCalendar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//@Temporal(TemporalType.DATE)
	private LocalDate startDate;
	//@Temporal(TemporalType.DATE)
	private LocalDate endDate;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "SESSIONCALENDAR_SESSION")
	@JoinColumn(name = "sessionCalander_id")
	private List<Session> sessions;
	

	protected SessionCalendar() {
		super();
	}
	
	public SessionCalendar(LocalDate startDate, LocalDate endDate) {
		
		if( startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate))
				throw new IllegalArgumentException("De tijden zijn niet correct");
		
		setStartDate(startDate);
		setEndDate(endDate);
		
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

	public void addSession(Session session) {
		sessions.add(session);
	}

	
}
