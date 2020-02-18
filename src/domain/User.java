package domain;

public class User {
	private String firtsName;
	private String lastName;
	private String userName;
	
	public User(String firtsName, String lastName, String userName) {
		this.firtsName = firtsName;
		this.lastName = lastName;
		this.userName = userName;
	}
	
	//getters and setters 
	private String getFirtsName() {
		return firtsName;
	}
	private void setFirtsName(String firtsName) {
		this.firtsName = firtsName;
	}
	private String getLastName() {
		return lastName;
	}
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}
	private String getUserName() {
		return userName;
	}
	private void setUserName(String userName) {
		this.userName = userName;
	}
}
