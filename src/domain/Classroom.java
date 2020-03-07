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
	private Campus campus;
	
	private int maxSeats;
	
	@Enumerated(EnumType.STRING)
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
		return "Classroom [id=" + classid + ", campus=" + campus + ", maxSeats=" + maxSeats + ", roomCategory="
				+ roomCategory + "]";
	}
	
	
	
	
	
}
