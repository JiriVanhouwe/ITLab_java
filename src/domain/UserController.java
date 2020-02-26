package domain;

public class UserController {
	
	private UserRepository userRepository;
	public User loggedInUser;
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public boolean isUserPassComboValid(String username, char[] password) {
		if(userRepository.isUserPassComboValid(username, password)) {
			loggedInUser = userRepository.getUser(username);
			return true;
		}
		return false;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}
}
