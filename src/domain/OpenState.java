package domain;

public class OpenState extends SessionState{

	@Override
	public void addAttendee(User user) {
		//Als de state 'open' is mogen er geen attendees worden toegevoegd
		throw new UnsupportedOperationException();
	}

	@Override
	public void registerAttendee(User user) {
		this.session.registerAttendee(user);
	}

}
