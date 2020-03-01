package domain;

public class FinishedState extends SessionState {

	@Override
	public void addAttendee(User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void registerAttendee(User user) {
		throw new UnsupportedOperationException();
	}

}
