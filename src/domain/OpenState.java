package domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity
//@DiscriminatorValue("OPEN")
public class OpenState extends SessionState{

	public OpenState() {
		// TODO Auto-generated constructor stub
	//	super.stateEnum = State.OPEN;
	}
	
	
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
