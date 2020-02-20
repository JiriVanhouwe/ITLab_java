package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Session {
	
	private String title;
	private String description;
	private String nameGuest;
	private String classRoom;
	private LocalDate startDate;
	private LocalDate endDate;
	private int maxAttendee;
	private boolean opened;
	private List<String> media;
	private List<User> registeredUsers;
	private List<User> attendees;
	private User host;
	private SessionReminder reminder;
	private List<Feedback> feedbackList;
	
	public Session() {
		
	}

	public Session(String title, String description, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee) {
		// TODO Auto-generated constructor stub
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

	private String getNameGuest() {
		return nameGuest;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	private void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	private void setEndDate(LocalDate endDate) {
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
	
	
	

}
