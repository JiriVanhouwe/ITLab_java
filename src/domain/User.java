package domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	//@NamedQuery(name="User.getAllUsers", query="SELECT u FROM User u"), 
	@NamedQuery(name="User.getUserByUserName", query=" SELECT u FROM User u WHERE :userName = u.userName ")
})
@Table(name="ItlabUser")
public class User {
	@Id
	private String userName;
	private String firstName;
	private String lastName;
	@Enumerated(EnumType.STRING)
	private UserType userType;
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	private String password;

	
	
	public User(String firstName, String lastName, String userName, UserType userType, UserStatus userStatus) {
		setFirstName(firstName);
		setLastName(lastName);
		setUserName(userName);
		setUserType(userType);
		setUserStatus(userStatus);
	}
	
	protected User() {
		
	}
	
	//methoden

	
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


	public UserType getUserType() {
		return userType;
	}

	private void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	private void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	
	

	public String getPassword() {
		return password;
	}

}
