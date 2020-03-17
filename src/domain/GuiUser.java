package domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public interface GuiUser {
	String giveUserType();
	String giveUserStatus();
	String getFirstName();
	String getLastName();
	String getUserName();
	UserType getUserType();
	UserStatus getUserStatus();
	String getPassword();
}
