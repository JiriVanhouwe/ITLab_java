package domain;

import java.time.LocalDate;

public class SessionReminder {
	
	private LocalDate sendDate;
	private String description;
	
	public SessionReminder(LocalDate sendDate, String description) {
		this.sendDate = sendDate;
		this.description = description;
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
