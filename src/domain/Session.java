package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.calendarfx.model.Entry;

@Entity
@NamedQueries({

})
@Table(name="Session")
public class Session{

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
	private List<String> media;
	
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
	
	public Session(String title, String description, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee,  Classroom classRoom, String nameGuest) {
		setTitle(title);
		setDescription(description);
		setDate(startDate.toLocalDate());
		setStartHour(startDate.toLocalTime());
		setEndHour(endDate.toLocalTime());
		setMaxAttendee(maxAttendee);
		setClassRoom(classRoom);
		setNameGuest(nameGuest);
		

		feedbackList = new ArrayList<>();
		registeredUsers = new ArrayList<>();
		attendees = new ArrayList<>();
		media = new ArrayList<>();
	}

	
	
	// methoden
	public void changeSession(String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee,  String description, String nameGuest) {
		setTitle(title);
		setDescription(description);
		setDate(startDate.toLocalDate());
		setStartHour(startDate.toLocalTime());
		setEndHour(endDate.toLocalTime());
		setMaxAttendee(maxAttendee);
		setClassRoom(classroom);
		setNameGuest(nameGuest);
		
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

	public Classroom getClassroom() {
		return classroom;
	}

	private void setClassRoom(Classroom classroom) {
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

	private void setMaxAttendee(int maxAttendee) {
		if (maxAttendee <= 0)
			throw new IllegalArgumentException("maxaanwezigen moeten groter dan nul zijn");
		this.maxAttendee = maxAttendee;
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

	public SessionState getState() {
		return state;
	}

	public void setState(SessionState state) {
		this.state = state;
	}

	
	
	

}
