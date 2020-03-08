package domain;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="SessionReminder")
public class SessionReminder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition = "DATE")
	private LocalDate sendDate;
	@Column(columnDefinition = "TIME")
	private LocalTime sendTime;
	private String description;
	
	public SessionReminder(LocalDateTime sendDate, String description) {
		setDescription(description);
		setSendDate(sendDate);
	}
	
	protected SessionReminder() {
		
	}
	
	//getters and setters

	private LocalDateTime getSendDate() {
		return sendDate.atTime(sendTime);
	}

	private void setSendDate(LocalDateTime sendDate) {
		this.sendDate = sendDate.toLocalDate();
		this.sendTime = sendDate.toLocalTime();
	}

	private String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
