package domain;

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
	
}
