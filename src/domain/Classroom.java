package domain;

import javax.persistence.*;

@Entity
public class Classroom {
	
	@Id
	private String classroomCode;
	
	@Enumerated(EnumType.STRING)
	private Campus campus;
	
	private int maxSeats;
	
	@Enumerated(EnumType.STRING)
	private ClassRoomCategory roomCategory;
	
	protected Classroom() {
		super();
	}

	public Classroom(String classroomCode, Campus campus, int maxSeats, ClassRoomCategory roomCategory) {
		this();
		this.classroomCode = classroomCode;
		this.campus = campus;
		this.maxSeats = maxSeats;
		this.roomCategory = roomCategory;
	}
	
	
	
	
	
}
