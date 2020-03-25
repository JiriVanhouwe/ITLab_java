package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FinishedState extends SessionState {
	
	public FinishedState(Session session) {
		this.session = session;
	}

	@Override
	public void changeTitle(String title) {
		throw new RuntimeException();
	}

	@Override
	public void changeGuest(String guestname) {
		throw new RuntimeException();
	}

	@Override
	public void changeDescription(String description) {
		throw new RuntimeException();
	}

	@Override
	public void changeClassroom(Classroom classroom) {
		throw new RuntimeException();
	}

	@Override
	public void changeEventDate(LocalDate date) {
		throw new RuntimeException();
	}

	@Override
	public void changeStartHour(LocalTime time) {
		throw new RuntimeException();
	}

	@Override
	public void changeEndHour(LocalTime time) {
		throw new RuntimeException();
	}

	@Override
	public void changeMaxAttendee(int max) {
		throw new RuntimeException();
	}

	@Override
	public void changeHost(User user) {
		throw new RuntimeException();
	}

	@Override
	public void changeMedia(List<Integer> media) {
		throw new RuntimeException();
	}

	@Override
	public void changeVideoURL(String url) {
		throw new RuntimeException();
	}

	@Override
	public void changeRegisteredUser(List<User> users) {
		throw new RuntimeException();
	}

	@Override
	public void changeAttendees(List<User> users) {
		throw new RuntimeException();
	}

	@Override
	public void changeFeedback(List<Feedback> feedback) {
		throw new RuntimeException();
	}



}
