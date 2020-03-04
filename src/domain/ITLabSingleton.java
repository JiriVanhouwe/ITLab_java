package domain;

public class ITLabSingleton {

	private static ITLab ITLAB;


	private ITLabSingleton() {}

	public static ITLab getITLabInstance() {
		if(ITLAB == null)
			ITLAB = new ITLab();
		return ITLAB;
	}

}
