package domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FINISHED")
public class FinishedState extends SessionState {

	public FinishedState() {
		// TODO Auto-generated constructor stub
		//super.stateEnum = State.FINISHED;
	}
	
	@Override
	public void addAttendee(User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void registerAttendee(User user) {
		throw new UnsupportedOperationException();
	}

}
