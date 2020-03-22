package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@NamedQueries({
	@NamedQuery(name="User.getAllUsers", query=" SELECT u FROM User u "), 
	@NamedQuery(name="User.getUserByUserName", query=" SELECT u FROM User u WHERE :userName = u.userName "),
	@NamedQuery(name="User.findEmailAddress", query=" SELECT u FROM User u WHERE :userName = u.userName ")
})
@Table(name="ItlabUser")
public class User implements GuiUser {
	@Id
	private String userName; //dit moet uniek zijn, e-mailadres HoGent en wordt opgeslagen in kleine letters!
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserType userType;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserStatus userStatus;

	@Column(nullable = false)
	private String password;
	
	
	public User(String firstName, String lastName, String userName, UserType userType, UserStatus userStatus, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setUserName(userName);
		setUserType(userType);
		setUserStatus(userStatus);
		setPassword(password);
	}
	
	protected User() {
		
	}
	
	//methoden
	public void changeUser(String firstName, String lastName, String userName, UserType userType, UserStatus userStatus, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setUserName(userName);
		setUserType(userType);
		setUserStatus(userStatus);
		setPassword(password);
	}
	
	public String giveUserType() {
		String res = "";
		switch(this.userType.toString()) { 
		case "HEAD" : res = "Hoofdverantwoordelijke"; break;
		case "USERITLAB" : res = "Gebruiker"; break;
		case "RESPONSIBLE" : res = "Verantwoordelijke"; break;
		}
		return res;
	}
	

	public String giveUserStatus() { 
		switch(this.userStatus) { 
			case ACTIVE : return "Actief"; 
			case BLOCKED : return "Geblokkeerd";
			case NONACTIVE : return "Niet-actief";
		}
		return null;
	}
	
	//getters and setters 
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.isBlank())
			throw new IllegalArgumentException("Voornaam moet ingevuld zijn.");
		if(!firstName.matches("([a-zA-Z]+\\s*[a-zA-Z]+)*"))
			throw new IllegalArgumentException("Voornaam mag enkel letters bevatten.");
		this.firstName = firstName.trim();
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		if (lastName == null || lastName.isBlank())
			throw new IllegalArgumentException("Familienaam moet ingevuld zijn.");
			if(!lastName.matches("([a-zA-Z]+\\s*[a-zA-Z]+)*"))
			throw new IllegalArgumentException("Familienaam mag enkel letters bevatten.");
		this.lastName = lastName.trim();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		if (userName == null || userName.isBlank())
			throw new IllegalArgumentException("Gebruikersnaam moet ingevuld zijn.");
		//if(!userName.toLowerCase().matches(firstName.toLowerCase() + "\\." + lastName.toLowerCase() + "@" +  "student.hogent.be") && !userName.toLowerCase().matches(firstName.toLowerCase() + "\\." + lastName.toLowerCase() + "@" +  "hogent.be"))
			//throw new IllegalArgumentException("Gebruikersnaam moet je HoGent e-mailadres zijn.");
		if(!userName.toLowerCase().matches( ".*" + "\\." + ".*" + "@" +  "student.hogent.be") && !userName.toLowerCase().matches(".*" + "\\." + ".*" + "@" +  "hogent.be"))
			throw new IllegalArgumentException("Gebruikersnaam moet je HoGent e-mailadres zijn.");
		if(!userName.toLowerCase().contains(firstName.replaceAll("\\s+", "").toLowerCase()) || !userName.toLowerCase().contains(lastName.replaceAll("\\s+", "").toLowerCase()))
			throw new IllegalArgumentException("De gebruikersnaam moet jouw voor- en familienaam bevatten.\nZoals: jan.peters@hogent.be");
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
	
	public void setPassword(String password) {
		if(password == null || password.isBlank())
			throw new IllegalArgumentException("Het wachtwoord mag niet leeg zijn.");
		this.password = password;
	}
	
	public StringProperty firstNameProperty() {
		return new SimpleStringProperty(firstName);
	}
	
	public StringProperty userNameProperty() {
		return new SimpleStringProperty(userName);
	}
	
	public StringProperty lastNameProperty() {
		return new SimpleStringProperty(lastName);
	}
	
	public StringProperty userTypeProperty() {
		return new SimpleStringProperty(giveUserType());
	}
	
	public StringProperty userStatusProperty() {
		return new SimpleStringProperty(giveUserStatus());
	}

}
