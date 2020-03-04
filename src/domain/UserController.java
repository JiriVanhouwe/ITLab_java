package domain;

public class UserController extends Controller {
	
	public UserController() {
		super();
	}
	
	public boolean isUserPassComboValid(String username, char[] password) {
		if(getItLab().isUserPassComboValid(username, password)) {
			itLab.setLoggedInUser(new User("Mister", "Adminman", "admin"));
			return true;
		}
		return false;
	}

	public User giveLoggedInUser() {
		return itLab.getLoggedInUser();
	}
	
}
