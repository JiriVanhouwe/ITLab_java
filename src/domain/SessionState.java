package domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


//@Entity
//@Inheritance
//@DiscriminatorColumn(name="State_Type")
//@Table(name = "SessionState")
public abstract class SessionState implements Serializable {

	public SessionState() {
		// TODO Auto-generated constructor stub
	}
	
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    protected int ID;
	
//	@OneToOne(mappedBy = "state")
	protected Session session;
	
	//protected State stateEnum;

	public abstract void addAttendee(User user);
	
	public abstract void registerAttendee(User user);
}
