package domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLOSED")
public class ClosedState extends SessionState {

	public ClosedState() {
		// TODO Auto-generated constructor stub
	//	super.stateEnum = State.CLOSED;
	}
	
	@Override
	public void addAttendee(User user) {
		this.session.addAttendee(user);
	}

	@Override
	public void registerAttendee(User user) {
		throw new UnsupportedOperationException();
	}

}
