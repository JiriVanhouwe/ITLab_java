package main;

import persistence.PersistenceController;

public class StartUp {

	public static void main(String[] args) {

		new PersistenceController();
		System.out.println("---Database created.---");

	}

}
