package domain;

public class UserController extends Controller {
	
	public UserController(ITLab itLab) {
		super(itLab);
	}
	
	public boolean isUserPassComboValid(String username, char[] password) {
		if(super.getItLab().isUserPassComboValid(username, password)) {
			return true;
		}
		return false;
	}

	
}
