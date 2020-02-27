package gui;

import java.io.IOException;

import domain.User;
import domain.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MainScreenController extends SplitPane {

    @FXML
    private AnchorPane sidebar;

    @FXML
    private Text name_txt;

    @FXML
    private ImageView name_arrow;

    @FXML
    private ImageView profilepicture_img;

    @FXML
    private HBox dashboard_box;

    @FXML
    private HBox calendar_box;

    @FXML
    private HBox responsibles_box;

    @FXML
    private HBox settings_box;

    @FXML
    private AnchorPane mainSection;
    
    private UserController usercontroller;
    
    public MainScreenController(Node node, UserController usercontroller) {
    	this.usercontroller = usercontroller;
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
    	loader.setController(this);
    	loader.setRoot(this);
    	
    	try{
    	      loader.load();
    	} 
    	catch (IOException ex){
    	      throw new RuntimeException(ex);
    	}
    	
    	this.mainSection.getChildren().add(node);
    	
    	
    	initializeScreen();
    }

	private void initializeScreen() {
		User user = usercontroller.giveLoggedInUser();
		name_txt.setText(user.getFirstName() + " " + user.getLastName());
	}
}
