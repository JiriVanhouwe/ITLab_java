package domain;

public abstract class Controller {
	protected ITLab itLab;
	
	public Controller() {
		setItLab(ITLabSingleton.getITLabInstance());
	}

	public ITLab getItLab() {
		return itLab;
	}

	private void setItLab(ITLab itLab) {
		this.itLab = itLab;
	}
	
}
