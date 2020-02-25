package domain;

import java.time.LocalDateTime;
import java.util.List;

import persistence.PersistenceController;

public class ITLabSingleton {
	
	private static ITLab ITLAB;
	


	private ITLabSingleton() {
	}
	
	public static ITLab getITLabInstance() {
		if(ITLAB == null)
			ITLAB = new ITLab();
		return ITLAB;
	}
	
	//methodes
	
	
	
}
