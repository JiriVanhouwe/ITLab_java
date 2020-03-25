package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class ClosedState extends SessionState {
	
	public ClosedState(Session session) {
		this.session = session;
	}

	@Override
	public void changeTitle(String title) {
		session.setTitle(title);
	}

	@Override
	public void changeGuest(String guestname) {
		session.setNameGuest(guestname);
	}

	@Override
	public void changeDescription(String description) {
		session.setDescription(description);
	}

	@Override
	public void changeClassroom(Classroom classroom) {
		session.setClassroom(classroom);
	}

	@Override
	public void changeEventDate(LocalDate date) {
		session.setDate(date);
	}

	@Override
	public void changeStartHour(LocalTime time) {
		session.setStartHour(time);
	}

	@Override
	public void changeEndHour(LocalTime time) {
		session.setEndHour(time);
	}

	@Override
	public void changeMaxAttendee(int max) {
		session.setMaxAttendee(max);
	}

	@Override
	public void changeHost(User user) {
		session.setHost(user);
	}

	@Override
	public void changeMedia(List<Integer> media) {
		session.setMedia(media);
	}

	@Override
	public void changeVideoURL(String url) {
		session.setVideoURL(url);
	}

	@Override
	public void changeRegisteredUser(List<User> users) {
		session.setRegisteredUsers(users);
	}

	@Override
	public void changeAttendees(List<User> users) {
		session.setAttendees(users);
	}

	@Override
	public void changeFeedback(List<Feedback> feedback) {
		session.setFeedbackList(feedback);
	}

}
