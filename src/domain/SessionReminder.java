package domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="SessionReminder")
public class SessionReminder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate sendDate;
	private String description;
	
	public SessionReminder(LocalDate sendDate, String description) {
		this.sendDate = sendDate;
		this.description = description;
	}
	
	protected SessionReminder() {
		
	}
	
	//getters and setters

	private LocalDate getSendDate() {
		return sendDate;
	}

	private void setSendDate(LocalDate sendDate) {
		this.sendDate = sendDate;
	}

	private String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
