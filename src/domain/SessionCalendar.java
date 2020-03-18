package domain;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@NamedQueries({
	@NamedQuery(name = "SessionCal.findCurCal", 
			query = "Select sc from SessionCalendar sc"
					+ " where sc.id = :id"),
	@NamedQuery(name = "SessoinCal.findAll", query = "Select sc from SessionCalendar sc")
})
@Table(name="SessionCalendar")
public class SessionCalendar implements GuiSessionCalendar {

	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(columnDefinition = "DATE")
	private LocalDate startDate;
	@Column(columnDefinition = "DATE")
	private LocalDate endDate;
	 @OneToMany(targetEntity=Session.class,cascade = CascadeType.ALL, 
             fetch = FetchType.LAZY, orphanRemoval = true)
	 @JoinColumn(name = "SessionCalendarId", referencedColumnName = "id")
	private List<Session> sessions;
	

	protected SessionCalendar() {
		super();
	}
	
	public SessionCalendar(int id ,LocalDate startDate, LocalDate endDate) {
		
		if( startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate))
				throw new IllegalArgumentException("De tijden zijn niet correct");
		
		setStartDate(startDate);
		setEndDate(endDate);
		setId(id);
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
	
	public void setId(int id) {
		if( String.valueOf(id).length() != 8)
			throw new IllegalArgumentException("id moet bestaan uit schooljaar dus twee jaartallen zoals 20192020");
		
		int deel1 = Integer.parseInt(String.valueOf(id).substring(0,4));
		int deel2 = Integer.parseInt(String.valueOf(id).substring(4,8));
		System.out.println(deel1+1);
		System.out.println(deel2);
		if((deel1 + 1) != deel2 )
			throw new IllegalArgumentException("id moet bestaan uit die opeenvolgende jaartallen");
		this.id = id;
	}

	
	public int getId() {
		return this.id;
	}

	public void addSession(Session session) {
		sessions.add(session);
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
	
	public Session giveSession(int id) {
		return sessions.stream().filter(session -> session.getSessionID() == id).findFirst().orElse(null);
	}
	
	public StringProperty sessionCalendarYearProperty() {
		return new SimpleStringProperty(Integer.toString(id));
	}
	
	public StringProperty sessionCalendarStartDateProperty() {
		return new SimpleStringProperty(startDate.toString());
	}

	public StringProperty sessionCalendarEndDateProperty() {
		return new SimpleStringProperty(endDate.toString());
	}
}
