package domain;

public abstract class SessionState {
	
	protected Session session;

	public abstract void addAttendee(User user);
	
	public abstract void registerAttendee(User user);
}
