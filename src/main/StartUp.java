package main;

import java.time.LocalDate;

import domain.GuiUser;
import domain.ITLab;
import domain.Session;
import domain.SessionCalendar;
import domain.SessionCalendarController;
import domain.SessionController;
import domain.User;
import domain.UserController;

public class StartUp {

	public static void main(String[] args) {
		
		SessionCalendarController scc = new SessionCalendarController();
		SessionController sc = new SessionController();
		UserController uc = new UserController();
//		uc.giveAllUsers().forEach(e -> System.out.println(e.getUserName()));
//		
//System.out.println(scc.giveSessionCalendar().toString());
//		sc.giveSessionsCurrentCalendar().stream().forEach(e -> System.out.println(e.toString()));
//		sc.giveAllClassrooms().stream().forEach(e -> System.out.println("klasse: "+ e.toString()));
//		System.out.println("test");
		//new PersistenceController(new ITLab());
		//System.out.println("---Database created.---");
		//Session session = sc.giveSession("1");
		//User user = (User) uc.giveUser("artuur.everaert@student.hogent.be");
		//session.addAttendee(user);
	}

}
