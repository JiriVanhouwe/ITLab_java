package gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MainScreenController extends SplitPane {

    @FXML
    private AnchorPane sidebar;

    @FXML
    private Text firstname_txt;

    @FXML
    private Text lastname_txt;

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
    
    public MainScreenController(Node node) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("WelkomScherm.fxml"));
    	loader.setController(this);
    	loader.setRoot(this);
    	
    	this.sidebar.getChildren().add(node);
    	
    	
    	try{
    	      loader.load();
    	} 
    	catch (IOException ex){
    	      throw new RuntimeException(ex);
    	}

    }
}
