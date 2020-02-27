package main;

import domain.ITLab;
import persistence.PersistenceController;

public class StartUp {

	public static void main(String[] args) {

		new PersistenceController(new ITLab());
		System.out.println("---Database created.---");

	}

}
