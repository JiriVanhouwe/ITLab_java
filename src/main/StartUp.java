package main;

import domain.ITLab;
import domain.SessionCalendar;
import domain.SessionCalendarController;
import domain.SessionController;
import domain.User;
import domain.UserController;
import persistence.PersistenceController;

public class StartUp {

	public static void main(String[] args) {
		
		ITLab itlab = new ITLab();
		
		SessionCalendarController scc = new SessionCalendarController();
		SessionController sc = new SessionController();
		
		sc.giveAllClassrooms().stream().forEach(e -> System.out.println("klasse: "+ e.toString()));
		
		//new PersistenceController(new ITLab());
		//System.out.println("---Database created.---");

	}

}
