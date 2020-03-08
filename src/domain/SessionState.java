package domain;

import java.io.Serializable;

public abstract class SessionState implements Serializable {
	
	
	protected Session session;

	public abstract void addAttendee(User user);
	
	public abstract void registerAttendee(User user);
}
