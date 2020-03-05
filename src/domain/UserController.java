package domain;

import java.util.List;

public class UserController extends Controller {
	
	public UserController() {
		super();
	}
	
	public boolean isUserPassComboValid(String username, char[] password) {
		return getItLab().isUserPassComboValid(username, password);
	}

	public User giveLoggedInUser() {
		return itLab.getLoggedInUser();
	}
	
	public User giveUser(String userName) {
		return itLab.getUserByUsername(userName);
	}
	
	public void createUser(String firstName, String lastName, String userName, UserType userType, UserStatus userStatus) {
		//TODO
	}
	
	public void deleteUser(String userName) {
		//TODO
	}
	
	
	
}
