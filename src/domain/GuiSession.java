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
	List<Feedback> getFeedbackList();
	List<Integer> getMedia();
	List<User> getAttendees();
	List<User> getRegisteredUsers();
	int getSessionID();
	String getVideoURL();
	void updateState();
	State getStateEnum();
}
