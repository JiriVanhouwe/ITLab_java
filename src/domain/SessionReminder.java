package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
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
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime sendDate;
	private String description;
	
	public SessionReminder(LocalDateTime sendDate, String description) {
		this.sendDate = sendDate;
		this.description = description;
	}
	
	protected SessionReminder() {
		
	}
	
	//getters and setters

	private LocalDateTime getSendDate() {
		return sendDate;
	}

	private void setSendDate(LocalDateTime sendDate) {
		this.sendDate = sendDate;
	}

	private String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
