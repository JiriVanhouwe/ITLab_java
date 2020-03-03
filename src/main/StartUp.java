package main;

import domain.ITLab;
import domain.SessionCalendar;
import domain.SessionCalendarController;
import domain.SessionController;
import domain.UserController;
import persistence.PersistenceController;

public class StartUp {

	public static void main(String[] args) {
		
		ITLab itlab = new ITLab();
		SessionCalendarController scc = new SessionCalendarController(itlab);
		SessionController sc = new SessionController(itlab);
		
		System.out.println(scc.giveSessionCalendar().toString());
		
		//new PersistenceController(new ITLab());
		//System.out.println("---Database created.---");

	}

}
