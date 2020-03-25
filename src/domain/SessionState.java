package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

abstract class SessionState{

	protected Session session;
	
	public void changeTitle(String title) {
		throw new UnsupportedOperationException();
	}
	
	public void changeGuest(String guestname) {
		throw new UnsupportedOperationException();
	}
	
	public void changeDescription(String description) {
		throw new UnsupportedOperationException();
	}
	
	public void changeClassroom(Classroom classroom) {
		throw new UnsupportedOperationException();
	}
	
	public void changeEventDate(LocalDate date) {
		throw new UnsupportedOperationException();
	}
	
	public void changeStartHour(LocalTime time) {
		throw new UnsupportedOperationException();
	}
	
	public void changeEndHour(LocalTime time) {
		throw new UnsupportedOperationException();
	}
	
	public void changeMaxAttendee(int max) {
		throw new UnsupportedOperationException();
	}
	
	public void changeHost(User user) {
		throw new UnsupportedOperationException();
	}
	
	public void changeMedia(List<Integer> media) {
		throw new UnsupportedOperationException();
	}
	
	public void changeVideoURL(String url) {
		throw new UnsupportedOperationException();
	}
	
	public void changeRegisteredUser(List<User> users) {
		throw new UnsupportedOperationException();
	}
	
	public void changeAttendees(List<User> users) {
		throw new UnsupportedOperationException();
	}
	
	public void changeFeedback(List<Feedback> feedback) {
		throw new UnsupportedOperationException();
	}
	
}
