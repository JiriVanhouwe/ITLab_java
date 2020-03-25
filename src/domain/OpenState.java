package domain;

import java.util.List;

class OpenState extends SessionState{
	
	public OpenState(Session session) {
		this.session = session;
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
