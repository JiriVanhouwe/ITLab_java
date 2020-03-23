package gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import domain.GuiUser;
import domain.ITLab;
import domain.User;
import domain.UserController;
import domain.UserStatus;
import domain.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.converter.DefaultStringConverter;

public class UsersController extends AnchorPane{ 
	
	private UserController userController;
	private String selectedUser = "";
	
    @FXML
    private JFXTextField txfSearch;

    @FXML
    private Button btnNewUser;

    @FXML
    private Button btnChangeUser;

    @FXML
    private Button btnDeleteUser;
    
    @FXML
    private Label lblMessage;
    
    @FXML
    private TableView<GuiUser> tableViewUsers;

    @FXML
    private TableColumn<GuiUser, String> userNameColumn;

    @FXML
    private TableColumn<GuiUser, String> firstNameColumn;

    @FXML
    private TableColumn<GuiUser, String> lastNameColumn;

    @FXML
    private TableColumn<GuiUser, String> typeColumn;

    @FXML
    private TableColumn<GuiUser, String> statusColumn;

    private Map<GuiUser, String> userTypeChanges;
    private Map<GuiUser, String> userStatusChanges;
    
    
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
		
		//Create new maps where will store all changed values
		userTypeChanges = new HashMap<GuiUser, String>();
		userStatusChanges = new HashMap<GuiUser, String>();
		
		//Fill the columns with the correct information, we use StringProperty in de User class
		userNameColumn.setCellValueFactory(data -> data.getValue().userNameProperty());
		firstNameColumn.setCellValueFactory(data -> data.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(data -> data.getValue().lastNameProperty());
		
		//Fill the cells in the 'type' column with comboboxes and all the possible usertypes
		ObservableList<String> typeList = FXCollections.observableArrayList("Hoofdverantwoordelijke", "Verantwoordelijke", "Gebruiker");
		typeColumn.setCellValueFactory(data -> data.getValue().userTypeProperty());
		typeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(typeList));
		typeColumn.setOnEditCommit(e -> {
			userTypeChanges.put(e.getRowValue(), e.getNewValue());
		});
		
		//Same thing for the 'status' column
		ObservableList statusList = FXCollections.observableArrayList("Actief", "Geblokkeerd", "Niet actief");
		statusColumn.setCellValueFactory(data -> data.getValue().userStatusProperty());
		statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn(statusList));
		statusColumn.setOnEditCommit(e -> {
			userStatusChanges.put(e.getRowValue(), e.getNewValue());
		});
		
		tableViewUsers.setItems(userController.giveAllUsers());
		
		   tableViewUsers.getSelectionModel()
	        .selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
	        	//check of item geselecteerd wordt
	        	if(newValue != null) {
	        		selectedUser = newValue.getUserName();
	        	}
	        });
	}
	
	
    @FXML
    void btnChangeUserClick(MouseEvent event) {
    	ObservableList<GuiUser> users = tableViewUsers.getItems();
    	//We loop over all changes in the changes-maps, then we use changeUser to commit the changes
    	for(Map.Entry<GuiUser, String> entry : userTypeChanges.entrySet()) {
    		GuiUser u = entry.getKey();
    		String type = entry.getValue();
    		userController.changeUser(u.getFirstName(), u.getLastName(), u.getUserName(), userController.stringToUserType(type) , u.getUserStatus());
    	}
    	
    	for(Map.Entry<GuiUser, String> entry : userStatusChanges.entrySet()) {
    		GuiUser u = entry.getKey();
    		String status = entry.getValue();
    		userController.changeUser(u.getFirstName(), u.getLastName(), u.getUserName(), u.getUserType() , userController.stringToUserStatus(status));
    	}
    	
    	lblMessage.setText("De wijzigingen zijn toegepast"); 
    	
    }

    @FXML
    void btnDeleteUserClick(MouseEvent event) {
    	if(selectedUser != null && !selectedUser.isBlank()) {
    		userController.deleteUser(selectedUser);
    		tableViewUsers.getItems().remove(selectedUser);
    		tableViewUsers.refresh();
    	} else {
    		lblMessage.setText("Gelieve eerst een gebruiker te selecteren.");
    	}
    	tableViewUsers.getSelectionModel().clearSelection();
    }

    @FXML
    void btnNewUserClick(MouseEvent event) {
		//Open nieuw scherm
		Scene scene = new Scene(new MakeUserController(userController));
		
		Stage stage = new Stage();

		stage.getIcons().add(new Image("/resources/ITLAB_logo_round.png"));
		stage.setTitle("ITLab");
		stage.setScene(scene);
		stage.setResizable(false);
		//This makes sure the new stage stays on top and can't be sent to the background
		stage.setAlwaysOnTop(true);
		stage.showAndWait();
		
		tableViewUsers.setItems(userController.giveAllUsers());
    }

    @FXML
    void searchFilter(KeyEvent event) {
    	String newValue = txfSearch.getText();
    	filterUsers(newValue);	
    }


	public void filterUsers(String filter) {
		ObservableList<GuiUser> filteredUsers = FXCollections.observableArrayList();
		String lowerCaseFilter = filter.toLowerCase();
		for(GuiUser user : userController.giveAllUsers()) {
			//We check all the properties of every user to see if one of them contains the text we're looking for
			if(user.getFirstName().toLowerCase().contains(lowerCaseFilter) 
			|| user.getLastName().toLowerCase().contains(lowerCaseFilter)
			|| user.getUserName().toLowerCase().contains(lowerCaseFilter)
			|| user.giveUserStatus().toLowerCase().contains(lowerCaseFilter)
			|| user.giveUserType().toLowerCase().contains(lowerCaseFilter)) {
				filteredUsers.add(user);
			}
		}
		tableViewUsers.setItems(filteredUsers);
	}

}
