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
	@NamedQuery(name="User.getAllUsers", query="SELECT u FROM User u"), 
	@NamedQuery(name="User.getUserByUserName", query=" SELECT u FROM User u WHERE :userName = u.userName ")
})
@Table(name="ItlabUser")
public class User {
	@Id
	private String userName; //dit moet uniek zijn, e-mailadres HoGent en wordt opgeslagen in kleine letters!
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
	public void changeUser(String firstName, String lastName, String userName, UserType userType, UserStatus userStatus) {
		setFirstName(firstName);
		setLastName(lastName);
		setUserName(userName);
		setUserType(userType);
		setUserStatus(userStatus);
	}

	//getters and setters 
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.isBlank())
			throw new IllegalArgumentException("Voornaam moet ingevuld zijn.");
		if(!firstName.matches("[a-zA-Z]+"))
			throw new IllegalArgumentException("Voornaam mag enkel letters bevatten.");
		this.firstName = firstName.trim();
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		if (lastName == null || lastName.isBlank())
			throw new IllegalArgumentException("Familienaam moet ingevuld zijn.");
		if(!lastName.matches("[a-zA-Z]+"))
			throw new IllegalArgumentException("Familienaam mag enkel letters bevatten.");
		this.lastName = lastName.trim();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		if (userName == null || userName.isBlank())
			throw new IllegalArgumentException("Gebruikersnaam moet ingevuld zijn.");
		if(!userName.toLowerCase().matches(firstName.toLowerCase() + "\\." + lastName.toLowerCase() + "@" +  "student.hogent.com") && !userName.toLowerCase().matches(firstName.toLowerCase() + "\\." + lastName.toLowerCase() + "@" +  "hogent.com"))
			throw new IllegalArgumentException("Gebruikersnaam moet je HoGent e-mailadres zijn.");
		this.userName = userName.trim();
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		if(userType == null)
			throw new IllegalArgumentException("Type gebruiker moet ingevuld zijn.");
		this.userType = userType;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		if(userStatus == null)
			throw new IllegalArgumentException("Status gebruiker moet ingevuld zijn.");
		this.userStatus = userStatus;
	}
	
	public String getPassword() {
		return password;
	}

}
