package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sessionID;
	private String title;
	private String description;
	private String nameGuest;
	private String classRoom;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime startDate;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime endDate;
	private int maxAttendee;
	private boolean opened;
	@ElementCollection
	private List<String> media;
	@ManyToMany
	private List<User> registeredUsers;
	@ManyToMany
	private List<User> attendees;
	@OneToOne
	private User host;
	private SessionReminder reminder;
	@OneToMany
	private List<Feedback> feedbackList;

	public Session(String title, String description, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee,
			String classRoom, String nameGuest) {
		this(title, classRoom, startDate, endDate, maxAttendee);
		setDescription(description);
		setNameGuest(nameGuest);
	}

	public Session(String title, String classRoom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee) {
		setTitle(title);
		setDescription(description);
		setStartDate(startDate);
		setEndDate(endDate);
		setMaxAttendee(maxAttendee);
		setClassRoom(classRoom);
		setNameGuest(nameGuest);
		setOpened(false);

		feedbackList = new ArrayList<>();
		registeredUsers = new ArrayList<>();
		attendees = new ArrayList<>();
		media = new ArrayList<>();
	}

	// methoden
	public void changeSession(String title, String description, LocalDateTime startDate, LocalDateTime endDate,
			int maxAttendee, String classRoom, String nameGuest) {
		setTitle(title);
		setDescription(description);
		setStartDate(startDate);
		setEndDate(endDate);
		setMaxAttendee(maxAttendee);
		setClassRoom(classRoom);
		setNameGuest(nameGuest);
	}

	// getters and setters
	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		if (title == null || title.isBlank())
			throw new IllegalArgumentException("titel moet ingevuld zijn");
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
		if (classRoom == null || classRoom.isBlank())
			throw new IllegalArgumentException("lokaal moet ingevuld zijn");
		this.classRoom = classRoom;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	private void setStartDate(LocalDateTime startDate) {
		if (endDate.minusMinutes(30).isBefore(startDate))
			throw new IllegalArgumentException("einduur moet minimaal 30 minuten na het startuur liggen");

		if (LocalDateTime.now().isAfter(startDate))
			throw new IllegalArgumentException("startdatum moet in de toekomst liggen");
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	private void setEndDate(LocalDateTime endDate) {
		if (LocalDateTime.now().isAfter(endDate))
			throw new IllegalArgumentException("einddatum moet in de toekomst liggen");
		this.endDate = endDate;
	}

	public int getMaxAttendee() {
		return maxAttendee;
	}

	private void setMaxAttendee(int maxAttendee) {
		if (maxAttendee <= 0)
			throw new IllegalArgumentException("maxaanwezigen moeten groter dan nul zijn");
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
