package domain;

public abstract class Controller {
	protected ITLab itLab;
	
	public Controller(ITLab itLab) {
		setItLab(itLab);
	}

	public ITLab getItLab() {
		return itLab;
	}

	private void setItLab(ITLab itLab) {
		this.itLab = itLab;
	}
	
}