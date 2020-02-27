package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
	
	private int sessionID;
	private String title;
	private String description;
	private String nameGuest;
	private String classRoom;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int maxAttendee;
	private boolean opened;
	private List<String> media;
	private List<User> registeredUsers;
	private List<User> attendees;
	private User host;
	private SessionReminder reminder;
	private List<Feedback> feedbackList;
	
	

	public Session(String title, String description, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee,String classRoom, String nameGuest) {
		this(title, classRoom ,startDate,endDate,maxAttendee);
		setDescription(description);
		setNameGuest(nameGuest);
	}
	
	public Session(String title, String classRoom , LocalDateTime startDate, LocalDateTime endDate, int maxAttendee) {
			
			
			if(title == null||title.isBlank())
				throw new IllegalArgumentException("title moet ingevuld zijn");
			setTitle(title);
			
			if(classRoom  == null||classRoom.isBlank())
				throw new IllegalArgumentException("lokaal moet ingevuld zijn");
			setClassRoom(classRoom);
			
			if(endDate.minusMinutes(30).isBefore(startDate) || startDate.isBefore(LocalDateTime.now().plusDays(1))) 
				throw new IllegalArgumentException("eind uur moet minmaal 30 min achter start uur liggen");
			setStartDate(startDate);
			setEndDate(endDate);
			
			if(maxAttendee <= 0)
				throw new IllegalArgumentException("maxaanwezigen moeten groter dan nul zijn");
			setMaxAttendee(maxAttendee);
			
			setOpened(false);
			
			feedbackList = new ArrayList<>();
			registeredUsers  = new ArrayList<>();
			attendees = new ArrayList<>();
			media = new ArrayList<>();
			
		}
	
	//getters and setters
	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}


	private void setNameGuest(String nameGuest) {
		this.nameGuest = nameGuest;
	}

	public String getClassRoom() {
		return classRoom;
	}

	private void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	private void setStartDate(LocalDateTime startDate) {
		if(LocalDateTime.now().isAfter(startDate))
			throw new IllegalArgumentException("startdatum moet in de toekomst liggen");
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	private void setEndDate(LocalDateTime endDate) {
		if(LocalDateTime.now().isAfter(endDate))
			throw new IllegalArgumentException("einddatum moet in de toekomst liggen");
		this.endDate = endDate;
	}

	public int getMaxAttendee() {
		return maxAttendee;
	}

	private void setMaxAttendee(int maxAttendee) {
		this.maxAttendee = maxAttendee;
	}

	private boolean isOpened() {
		return opened;
	}

	private void setOpened(boolean opened) {
		this.opened = opened;
	}

	private List<String> getMedia() {
		return media;
	}

	private void setMedia(List<String> media) {
		this.media = media;
	}

	private List<User> getRegisteredUsers() {
		return registeredUsers;
	}

	private void setRegisteredUsers(List<User> registeredUsers) {
		this.registeredUsers = registeredUsers;
	}

	private List<User> getAttendees() {
		return attendees;
	}

	private void setAttendees(List<User> attendees) {
		this.attendees = attendees;
	}

	private User getHost() {
		return host;
	}

	private void setHost(User host) {
		this.host = host;
	}

	private SessionReminder getReminder() {
		return reminder;
	}

	private void setReminder(SessionReminder reminder) {
		this.reminder = reminder;
	}

	private List<Feedback> getFeedbackList() {
		return feedbackList;
	}

	private void setFeedbackList(List<Feedback> feedbackList) {
		this.feedbackList = feedbackList;
	}

	public int getSessionID() {
		// TODO Auto-generated method stub
		return this.sessionID;
	}
	
	
	

}
