package domain;

public class ClosedState extends SessionState {

	@Override
	public void addAttendee(User user) {
		this.session.addAttendee(user);
	}

	@Override
	public void registerAttendee(User user) {
		throw new UnsupportedOperationException();
	}

}
