package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javafx.beans.property.StringProperty;

public interface GuiSession {
	String getTitle();
	String getDescription();
	LocalTime getEndHour();
	LocalTime getStartHour();
	LocalDate getDate();
	int getMaxAttendee();
	List<Integer> getMedia();
	List<GuiUser> getAttendeesGui();
	List<User> getRegisteredUsers();
	int getSessionID();
	
	StringProperty sessionTitleProperty();
	StringProperty sessionDateProperty();
	StringProperty sessionStartHourProperty();
	StringProperty sessionEndHourProperty();
}
