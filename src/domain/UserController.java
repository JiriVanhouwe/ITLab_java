package domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserController extends Controller {

	public UserController() {
		super();
	}

	public boolean isUserPassComboValid(String username, String password) {
		return getItLab().isUserPassComboValid(username, password);
	}

	public GuiUser giveLoggedInUser() {
		return itLab.getLoggedInUser();
	}

	public boolean isUserHeadOrResponsible() { // head = true responsible = false
		return itLab.isUserHeadOrResponsible();
	}

	public GuiUser giveUser(String userName) {
		return (GuiUser) giveCleanUser(userName);
	}

	public ObservableList<User> giveAllUsers() {
		return itLab.getAllUsers().sorted(Comparator.comparing(User::getFirstName).thenComparing(User::getLastName));
	}

	public void changeUser(String firstName, String lastName, String userName, UserType userType, UserStatus userStatus,
			String password) {
		User user = giveCleanUser(userName);
		if (user != null) {
			String hashedPassword = this.hashPassword(password);
			user.changeUser(firstName, lastName, userName, userType, userStatus, hashedPassword);
			itLab.getEntityManager().getTransaction().begin();
			itLab.getEntityManager().persist(user);
			itLab.getEntityManager().getTransaction().commit();
		}
	}

	public void createUser(String firstName, String lastName, String userName, UserType userType, UserStatus userStatus,
			String password) {

		String hashedPassword = this.hashPassword(password);
		User user = new User(firstName, lastName, userName, userType, userStatus, hashedPassword);
		itLab.getEntityManager().getTransaction().begin();
		itLab.getEntityManager().persist(user);
		itLab.getEntityManager().getTransaction().commit();
	}

	public void changePassword(String userName, String password) {
		String hashedPassword = this.hashPassword(password);

		User user = giveCleanUser(userName);
		user.setPassword(hashedPassword);
		itLab.getEntityManager().getTransaction().begin();
		itLab.getEntityManager().persist(user);
		itLab.getEntityManager().getTransaction().commit();
	}

	public void deleteUser(String userName) {
		User user = giveCleanUser(userName);
		itLab.getEntityManager().getTransaction().begin();
		itLab.getEntityManager().remove(user);
		itLab.getEntityManager().getTransaction().commit();
	}

	public void changeFilter(String filter) {
		itLab.changeFilter(filter);
	}

	private User giveCleanUser(String userName) {
		try {
			return itLab.getEntityManager().createNamedQuery("User.getUserByUserName", User.class)
					.setParameter("userName", userName).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public User findEmailAddress(String email) {
		try {
			return itLab.getEntityManager().createNamedQuery("User.findEmailAddress", User.class)
					.setParameter("userName", email).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	private String hashPassword(String password) {
		// Password hashing
		String hashedPassword;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			hashedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}

		return hashedPassword;
	}

	public UserType stringToUserType(String type) {
		switch (type) {
		case "Hoofdverantwoordelijke":
			return UserType.HEAD;
		case "Verantwoordelijke":
			return UserType.RESPONSIBLE;
		case "Gebruiker":
			return UserType.USERITLAB;
			default: return null;
		}
	}

	public UserStatus stringToUserStatus(String status) {
		switch (status) {
		case "Actief":
			return UserStatus.ACTIVE;
		case "Geblokkeerd":
			return UserStatus.BLOCKED;
		case "Nie- actief":
			return UserStatus.NONACTIVE;
			default: return null;
		}
	}
}
