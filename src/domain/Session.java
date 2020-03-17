package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name="Session")
public class Session implements GuiSession{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sessionID;
	private String title;
	private String description;
	private String nameGuest;
	@ManyToOne(cascade = CascadeType.DETACH)
	private Classroom classroom;
	
	@Column(columnDefinition = "DATE")
	private LocalDate eventDate;
	
	@Column(columnDefinition = "TIME")
	private LocalTime startHour;
	
	@Column(columnDefinition = "TIME")
	private LocalTime endHour;
	
	private int maxAttendee;
	@ElementCollection
	@JoinTable(name = "Session_Media")
	private List<Integer> media;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<User> registeredUsers;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<User> attendees;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private User host;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private SessionReminder reminder;
	
	@OneToMany(targetEntity=Feedback.class,cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "SessionId", referencedColumnName = "sessionID")
	private List<Feedback> feedbackList;
	
	@Transient
	private SessionState state;
	
	protected Session() {
		
	}
	
	public Session(String title, String description, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee,  Classroom classRoom, String nameGuest, List<Integer> media) {
		setTitle(title);
		setDescription(description);
		setDate(startDate.toLocalDate());
		setStartHour(startDate.toLocalTime());
		setEndHour(endDate.toLocalTime());
		setClassroom(classRoom);
		setMaxAttendee(maxAttendee);
		setNameGuest(nameGuest);
		setMedia(media);

		feedbackList = new ArrayList<>();
		registeredUsers = new ArrayList<>();
		attendees = new ArrayList<>();
	}

	
	
	// methoden
	public void changeSession(String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee,  String description, String nameGuest, List<Integer> media) {
		setTitle(title);
		setDescription(description);
		setDate(startDate.toLocalDate());
		setStartHour(startDate.toLocalTime());
		setEndHour(endDate.toLocalTime());
		setClassroom(classroom);
		setMaxAttendee(maxAttendee);
		setNameGuest(nameGuest);
		setMedia(media);
	}
	
	public void registerAttendee(User user) {
		this.registeredUsers.add(user);
	}
	
	public void addAttendee(User user) {
		this.attendees.add(user);
	}
	
	public void closenSession() {
		setState(new ClosedState());
	}
	
	public void finishSession() {
		setState(new FinishedState());
	}

	// getters and setters
	public String getTitle() {
		return title;
	}

	protected void setTitle(String title) {
		this.title = title; 
	}

	public String getDescription() {
		return description;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	protected void setNameGuest(String nameGuest) {
		this.nameGuest = nameGuest;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	protected void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}


	public LocalTime getEndHour() {
		return endHour;
	}

	public void setEndHour(LocalTime endHour) {
			this.endHour = endHour;
	}

	public LocalTime getStartHour() {
		return startHour;
	}

	public void setStartHour(LocalTime startHour) {
		this.startHour = startHour;
	}

	public LocalDate getDate() {
		return eventDate;
	}

	public void setDate(LocalDate date) {
		this.eventDate = date;
	}

	public int getMaxAttendee() {
		
		return maxAttendee;
	}

	protected void setMaxAttendee(int maxAttendee) {
		this.maxAttendee = maxAttendee;
	}

	public List<Integer> getMedia() {
		return media;
	}

	protected void setMedia(List<Integer> media) {
		this.media = media;
	}

	public List<User> getRegisteredUsers() {
		return registeredUsers;
	}

	protected void setRegisteredUsers(List<User> registeredUsers) {
		this.registeredUsers = registeredUsers;
	}

	public List<User> getAttendees() {
		return attendees;
	}

	protected void setAttendees(List<User> attendees) {
		this.attendees = attendees;
	}

	private User getHost() {
		return host;
	}

	private void setHost(User host) {
		this.host = host;
	}

	protected SessionReminder getReminder() {
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
		return this.sessionID;
	}

	public SessionState getState() {
		return state;
	}

	public void setState(SessionState state) {
		this.state = state;
	}

	public String getNameGuest() {
		return nameGuest;
	}
	
	public StringProperty sessionTitleProperty() {
		return new SimpleStringProperty(title);
	}
	
	public StringProperty sessionDateProperty() {
		return new SimpleStringProperty(eventDate.toString());
	}
	
	public StringProperty sessionStartHourProperty() {
		return new SimpleStringProperty(startHour.toString());
	}
	
	public StringProperty sessionEndHourProperty() {
		return new SimpleStringProperty(endHour.toString());
	}
}
