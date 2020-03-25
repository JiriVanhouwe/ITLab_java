package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public abstract class SessionState{

	protected Session session;
	
	public abstract void changeTitle(String title);
	public abstract void changeGuest(String guestname);
	public abstract void changeDescription(String description);
	public abstract void changeClassroom(Classroom classroom);
	public abstract void changeEventDate(LocalDate date);
	public abstract void changeStartHour(LocalTime time);
	public abstract void changeEndHour(LocalTime time);
	public abstract void changeMaxAttendee(int max);
	public abstract void changeHost(User user);
	public abstract void changeMedia(List<Integer> media);
	public abstract void changeVideoURL(String url);
	public abstract void changeRegisteredUser(List<User> users);
	public abstract void changeAttendees(List<User> users);
	public abstract void changeFeedback(List<Feedback> feedback);
	
}
