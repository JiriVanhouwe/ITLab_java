package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ITLab_USER")
public class User {
	@Id
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	
	public User(String firstName, String lastName, String userName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
	}
	
	protected User() {
		
	}
	
	//getters and setters 
	public String getFirstName() {
		return firstName;
	}
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	private void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}
}
