package domain;

import javax.persistence.*;

@Entity
@NamedQueries({
	@NamedQuery(name = "Classroom.findAll", query = "SELECT c from Classroom c"),
	@NamedQuery(name ="Classroom.findById", query = "SELECT c from Classroom c where c.classid = :classid ")
})
@Table(name="Classroom")
public class Classroom {
	
	@Id
	private String classid;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Campus campus;
	
	@Column(nullable = false)
	private int maxSeats;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ClassRoomCategory roomCategory;
	
	private Classroom() {
		super();
	}

	public Classroom(String classid, Campus campus, int maxSeats, ClassRoomCategory roomCategory) {
		this();
		this.classid = classid;
		this.campus = campus;
		this.maxSeats = maxSeats;
		this.roomCategory = roomCategory;
	}

	@Override
	public String toString() {
		return classid + ", Campus = " + campus + ", Seats = " + maxSeats + ", Category = "
				+ roomCategory;
	}

	public int getMaxSeats() {
		return maxSeats;
	}
	
	
	
	
	
}
