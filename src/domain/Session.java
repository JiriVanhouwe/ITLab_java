package domain;

import java.time.LocalDate;
import java.util.List;

public class Session {
	
	private String title;
	private String discription;
	private String nameGeust;
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

}
