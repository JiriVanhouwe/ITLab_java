package domain;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
		return  itLab.getEntityManager().createNamedQuery("User.getUserByUserName", User.class).setParameter("userName", userName).getSingleResult();
	}
	public ObservableList<User> giveAllUsers(){
		ObservableList<User> users = FXCollections.observableArrayList(itLab.getEntityManager().createNamedQuery("User.getAllUsers", User.class).getResultList());
		return FXCollections.unmodifiableObservableList(users).sorted(Comparator.comparing(User::getFirstName));
	}
	
	public void changeUser(String firstName, String lastName, String userName, UserType userType, UserStatus userStatus) {
		User user = giveUser(userName);
		user.changeUser(firstName, lastName, userName, userType, userStatus);
		itLab.getEntityManager().getTransaction().begin();
		itLab.getEntityManager().persist(user);
		itLab.getEntityManager().getTransaction().commit();
	}
	
	public void createUser(String firstName, String lastName, String userName, UserType userType, UserStatus userStatus) {
		//TODO checken of de user al bestaat adhv userName, mag dat hier?			
		User user = new User(firstName, lastName, userName, userType, userStatus);
		itLab.getEntityManager().getTransaction().begin();
		itLab.getEntityManager().persist(user);
		itLab.getEntityManager().getTransaction().commit();
	}
	
	public void deleteUser(String userName) {
		User user = giveUser(userName);
		itLab.getEntityManager().getTransaction().begin();
		itLab.getEntityManager().persist(user);
		itLab.getEntityManager().getTransaction().commit();
	}
	
	
	
}
