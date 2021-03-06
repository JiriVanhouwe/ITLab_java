package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Feedback")
public class Feedback implements GuiFeedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(nullable = false)
	private User author;

	@Column(nullable = false)
	private String contentText;
	
	protected Feedback() {
		
	}
	
	public Feedback(User user, String feedback) {
		setUser(user);
		setContentText(feedback);
	}
	
	private void setUser(User user) {
		if(user != null)
			author = user;
		else
			throw new IllegalArgumentException("De auteur van de feedback moet ingevuld zijn.");
	}
	
	public User getAuthor() {
		return author;
	}

	private void setContentText(String text) {
		if(text != null && !text.isBlank())
			contentText = text;
		else 
			throw new IllegalArgumentException("Feedback mag niet leeg zijn.");
	}
	
	public String getContentText() {
		return contentText;
	}
	
	public int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		
		return String.format("%d van %s", this.id, this.author.getFirstName());
	}
}
