package gui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import domain.User;
import domain.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class UsersController extends AnchorPane{ 
	
	private UserController userController;
	private String selectedUser;
	
    @FXML
    private JFXTextField txfSearch;

    @FXML
    private JFXButton btnNewUser;

    @FXML
    private JFXButton btnChangeUser;

    @FXML
    private JFXButton btnDeleteUser;
    
    @FXML
    private Label lblMessage;
    
    @FXML
    private TableView<User> tableViewUsers;

    @FXML
    private TableColumn<User, String> userNameColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> typeColumn;

    @FXML
    private TableColumn<User, String> statusColumn;
	
	public UsersController(UserController userController) {
		this.userController = userController;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Users.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		userNameColumn.setCellValueFactory(data -> data.getValue().userNameProperty());
		firstNameColumn.setCellValueFactory(data -> data.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(data -> data.getValue().lastNameProperty());
		typeColumn.setCellValueFactory(data -> data.getValue().userTypeProperty());
		statusColumn.setCellValueFactory(data -> data.getValue().userStatusProperty());
		
		tableViewUsers.setItems(userController.giveAllUsers());
		
		   tableViewUsers.getSelectionModel()
	        .selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
	        	//check of item geselecteerd wordt
	        	if(newValue != null) {
	        		System.out.println(newValue.getUserName());
	        		selectedUser = newValue.getUserName();
	        	}
	        });
	}
	
	
    @FXML
    void btnChangeUserClick(MouseEvent event) {
    	if(selectedUser != null || !selectedUser.isBlank()) {
    		
    	}

    }

    @FXML
    void btnDeleteUserClick(MouseEvent event) {
    	if(selectedUser != null || !selectedUser.isBlank()) {
    		userController.deleteUser(selectedUser);
    	} else {
    		//TODO hier loopt iets mis. Hij onthoudt de laatst geselecteerde. Ook al lijkt de selectie leeg te zijn.
    		lblMessage.setText("Gelieve eerst een gebruiker te selecteren.");
    	}
    	tableViewUsers.getSelectionModel().clearSelection();
    }

    @FXML
    void btnNewUserClick(MouseEvent event) {

    }

    @FXML
    void searchFilter(KeyEvent event) {
    	String newValue = txfSearch.getText();
    	userController.changeFilter(newValue);	
    }


	

}
