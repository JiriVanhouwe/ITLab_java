package domain;

import java.time.LocalDate;
import java.util.List;

public interface GuiSessionCalendar {
	LocalDate getStartDate();
	LocalDate getEndDate();
	List<Session> getSessions();
	Session giveSession(int id);
	int getId();
}
