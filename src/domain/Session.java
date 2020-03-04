package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.calendarfx.model.Entry;

@Entity
@NamedQueries({
	//@NamedQuery(name = "Session.findAllCurCal", 
	//		query = "SELECT s from Session s left join s.SESSIONCALENDAR_SESSION  scs"
	//				+ " on s.sessionid = scs.sessions_sessionid"
	//				+ " where scs.sessionCalendar_id = :scid ")
})
public class Session{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sessionID;
	private String title;
	private String description;
	private String nameGuest;
	@ManyToOne(cascade = CascadeType.ALL)
	private Classroom classRoom;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	private int maxAttendee;
	@ElementCollection
	private List<String> media;
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<User> registeredUsers;
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<User> attendees;
	@OneToOne(cascade = CascadeType.PERSIST)
	private User host;
	
	@Transient
	private SessionReminder reminder;
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<Feedback> feedbackList;
	
	@Transient
	private SessionState state;
	
	protected Session() {
		super();
	}
	
	public Session(String title,  Classroom classRoom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee,
			String description, String nameGuest) {
		this(title, classRoom, startDate, endDate, maxAttendee);
		setDescription(description);
		setNameGuest(nameGuest);
		setState(new OpenState());
	}

	public Session(String title, Classroom classRoom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee) {
		setTitle(title);
		setDescription(description);
		setStartDate(startDate);
		setEndDate(endDate);
		setMaxAttendee(maxAttendee);
		setClassRoom(classRoom);
		setNameGuest(nameGuest);
		setState(new OpenState());

		feedbackList = new ArrayList<>();
		registeredUsers = new ArrayList<>();
		attendees = new ArrayList<>();
		media = new ArrayList<>();
	}

	// methoden
	public void changeSession(String title, String description, LocalDateTime startDate, LocalDateTime endDate,
			int maxAttendee,  Classroom classRoom, String nameGuest) {
		setTitle(title);
		setDescription(description);
		setStartDate(startDate);
		setEndDate(endDate);
		setMaxAttendee(maxAttendee);
		setClassRoom(classRoom);
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

	public Classroom getClassRoom() {
		return classRoom;
	}

	private void setClassRoom(Classroom classRoom) {
		this.classRoom = classRoom;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	private void setStartDate(LocalDateTime startDate) {
		if(endDate != null) {
			if (endDate.minusMinutes(30).isBefore(startDate))
				throw new IllegalArgumentException("einduur moet minimaal 30 minuten na het startuur liggen");

			if (LocalDateTime.now().isAfter(startDate))
				throw new IllegalArgumentException("startdatum moet in de toekomst liggen");			
		}

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
