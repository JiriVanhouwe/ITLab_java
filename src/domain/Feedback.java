package domain;

import javax.persistence.Entity;

@Entity
public class Feedback {

	private User author;
	private boolean opened;
	private String content;
}
