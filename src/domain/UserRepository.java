package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRepository {

	private List<User> users;

	public UserRepository() {
		users = new ArrayList<User>();
		users.add(new User("Mister", "Adminman", "admin"));
	}

	public boolean isUserPassComboValid(String username, char[] password) {
		// controle of user bestaat
		for (User u : users) {
			if (u.getUserName().equals(username)) {
				return isValidPasswordForUser(username, password);
			}
		}
		return false;
	}

	private boolean isValidPasswordForUser(String username, char[] password) {
		// TODO: Dit is gewoon test-code, connectie met DB maken en PW opvragen uit tabel
		char[] pass = "admin".toCharArray();
		return (username.equals("admin") && Arrays.equals(pass, password));
	}

	public User getUser(String username) {
		for(User u : users) {
			if(u.getUserName().equals(username)) {
				return u;
			}
		}
		return null;
	}
}
