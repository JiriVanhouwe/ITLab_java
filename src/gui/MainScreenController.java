package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import domain.GuiUser;
import domain.SessionController;
import domain.UserController;
import domain.UserType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainScreenController extends SplitPane {

	@FXML
    private Label lblUserName;

    @FXML
    private HBox hbox_mainSection;
    
    @FXML
    private SplitPane rootPane;
    
    @FXML
    private Button btnUsers;
    
    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignOut;
    
    @FXML
    private Button btnCalendar;

    @FXML
    private Button btnSessionCalendars;

    @FXML
    private Button btnStatistics;
    
    @FXML
    private Button btnSwitchSize;

    @FXML
    private ImageView switchIcon;

    private UserController usercontroller;
    
    private SessionController sessionController;
    
    private UsersController uc;
    
   
    
    private Button _selectedButton;
    
    public MainScreenController(UserController usercontroller) {
    	this.usercontroller = usercontroller;
    	sessionController = new SessionController();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
    	loader.setController(this);
    	loader.setRoot(this);
    	
    	try{
    	      loader.load();
    	} 
    	catch (IOException ex){
    	      throw new RuntimeException(ex);
    	}  
    	
    	lblUserName.setText(usercontroller.giveLoggedInUser().getFirstName());
    	initializeScreen();
    	_selectedButton = btnCalendar;
    	changeMainSection(new CalendarController());
    	
    	//Only the head users can acces the user screen
    	if(this.usercontroller.giveLoggedInUser().getUserType() != UserType.HEAD) {
    		btnUsers.setDisable(true);
    	}
    }
    

	private void initializeScreen() {
		GuiUser user = usercontroller.giveLoggedInUser();
	}
	
    private void changeMainSection(Node node) {
    	if(hbox_mainSection.getChildren().size() > 0)
    		hbox_mainSection.getChildren().remove(0);
    	
    	HBox.setHgrow(node, Priority.ALWAYS);
    	hbox_mainSection.getChildren().add(node);
    }
    
    private void changeSelectedButton(Button button) {
    	_selectedButton.getStyleClass().remove("selected");
    	button.getStyleClass().add("selected");
    	_selectedButton = button;
    }
	
    @FXML
    void userNameClick(MouseEvent event) {
    	changeSelectedButton(btnSettings);
    	changeMainSection(new SettingsController(this.usercontroller));
    }
    
    @FXML
    void clickBtnCalendar(MouseEvent event) {
    	changeSelectedButton(btnCalendar);
    	changeMainSection(new CalendarController());
    }
    
    @FXML
    void clickBtnSessionCalendars(MouseEvent event) {
    	changeSelectedButton(btnSessionCalendars);
    	changeMainSection(new SessionCalendarsController());
    }
    
    @FXML
    void clickBtnStatistics(MouseEvent event) {
    	changeSelectedButton(btnStatistics);
    	changeMainSection(new StatsController(this.sessionController, this.usercontroller));
    }
    
    @FXML
    void clickBtnSettings(MouseEvent event) {
    	userNameClick(event);
    }
    
    @FXML
    void clickBtnUsers(MouseEvent event) {  	
//    	_selectedButton.getStyleClass().remove("selected");
//    	btnUsers.getStyleClass().add("selected");
//    	_selectedButton = btnUsers;
    	
    	
//    	hbox_mainSection.getChildren().clear();
//    	uc = new UsersController(this.usercontroller);
//    	hbox_mainSection.getChildren().add(uc);
    	changeSelectedButton(btnUsers);
    	changeMainSection(new UsersController(this.usercontroller));
    }
    
    @FXML
    void clickSignOut(MouseEvent event) {
    	//Toon het login scherm
    	//Sluit huidig scherm
		Stage curStage = (Stage) btnSignOut.getScene().getWindow();
		curStage.close();
		
		//Open nieuw scherm
		Scene scene = new Scene(new LogInController(usercontroller));

		Parent root = scene.getRoot();
		Stage stage = new Stage();
		UserController usercontroller = new UserController();

		stage.getIcons().add(new Image("/resources/ITLAB_logo_round.png"));
		stage.setTitle("Log in");
		stage.setScene(scene);

		
		// The stage will not get smaller than its preferred (initial) size.
		stage.setOnShown((WindowEvent t) -> {
			stage.setMinWidth(stage.getWidth());
			stage.setMinHeight(stage.getHeight());
		});
		
		
		stage.initStyle(StageStyle.UNDECORATED); 
		stage.show();
    }
    

    @FXML
    void clickSwitchSize(MouseEvent event) {
		Stage curStage = (Stage) btnSignOut.getScene().getWindow();
		curStage.setMaximized((!curStage.isMaximized()));
		if(switchIcon.getImage().getUrl().endsWith("expand-solid.png")) {
			switchIcon.setImage(new Image("file:src\\resources\\compress-solid.png"));
			btnSwitchSize.setText("Volledig scherm");

		} else {
			switchIcon.setImage(new Image("file:src\\resources\\expand-solid.png"));
			btnSwitchSize.setText("Verklein scherm");
		}
    }

}
