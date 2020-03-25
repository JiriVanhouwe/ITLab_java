package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.controlsfx.control.PopOver;

import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import domain.Classroom;
import domain.GuiSession;
import domain.GuiUser;
import domain.ITLab;
import domain.ITLabSingleton;
import domain.RequiredElement;
import domain.Session;
import domain.SessionCalendarController;
import domain.SessionController;
import domain.State;
import domain.UserType;
import exceptions.InformationRequiredException;
import exceptions.NotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ManageSessionController extends VBox {

	@FXML
    private JFXTextField title_txt;

    @FXML
    private JFXTextField speaker_txt;

    @FXML
    private ComboBox<Classroom> clasroom_dropdown;

    @FXML
    private JFXTextArea description_txt;

    @FXML
    private DatePicker start_date;

    @FXML
    private Label fromHour_txt;

    @FXML
    private Label toHour_txt;

    @FXML
    private JFXTextField image_txt;

    @FXML
    private JFXButton image_btn;

    @FXML
    private JFXButton addimage_btn;

    @FXML
    private VBox image_scrollpane;

    @FXML
    private JFXTextField url_txt;

    @FXML
    private Button savebtn;

    @FXML
    private Button reminderBtn;

    @FXML
    private Button cancelbtn;

    @FXML
    private Spinner<Integer> nrOfAttendeeSpinner;

    @FXML
    private JFXToggleButton sessionStateToggle;

    @FXML
    private Button deleteBtn;
    
    @FXML
    private Label errorTxt;

    private GuiSession clickedSession;
    
	private Entry entry;

	private Image selectedImage;
	private HashMap<Integer, Image> sessionMedia;

	private SessionController sessionController;
	private SessionCalendarController sessionCalendarController;

	public ManageSessionController(Entry entry) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageSession.fxml"));
		loader.setController(this);
		loader.setRoot(this);

		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		sessionController = new SessionController();
		sessionCalendarController = new SessionCalendarController();
		
		//Fill up all the fields with the correct information
		this.entry = entry;
		fillClassrooms();
		sessionMedia = new HashMap<Integer, Image>();

		clickedSession = sessionController.giveSession(entry.getId());
		this.title_txt.setText(entry.getTitle());
		this.start_date.setValue(entry.getStartDate());
		
		if (clickedSession != null && entry.getId().charAt(entry.getId().length() - 1) == '#') {
			this.description_txt.setText(clickedSession.getDescription());
			this.clasroom_dropdown.getSelectionModel().select(clickedSession.getClassroom());
			this.speaker_txt.setText(clickedSession.getNameGuest());
			this.url_txt.setText(clickedSession.getVideoURL());
			SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, clickedSession.getClassroom().getMaxSeats(), clickedSession.getMaxAttendee());
			nrOfAttendeeSpinner.setValueFactory(valueFactory);
			//Set the correct state to the toggle
			this.instantiateStateToggle();
			// Fill the image list
			loadImages();
		} else {
			SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
			nrOfAttendeeSpinner.setValueFactory(valueFactory);
		}

		// Formatting for the time
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
		this.fromHour_txt.setText(entry.getStartTime().format(timeFormat).toString());
		this.toHour_txt.setText(entry.getEndTime().format(timeFormat).toString());
		
		//We check if the user can modify this session
		checkUserPermissions();
	}

	private void fillClassrooms() {
		ObservableList<Classroom> classrooms = FXCollections
				.observableArrayList(ITLabSingleton.getITLabInstance().getClassrooms());
		clasroom_dropdown.getItems().addAll(classrooms);
	}

	private void loadImages() {
		for (Integer i : clickedSession.getMedia()) {
			this.sessionMedia.put(i, sessionController.giveImage(i));
		}

		sessionMedia.forEach((id, image) -> {
			if(image != null) {
				addImageToScrollPane(image);
			}
		});
	}

	@FXML
	void pressedCancelBtn(ActionEvent event) {
		this.close();
	}

	@FXML
	void pressedSaveBtn(ActionEvent event)  {
		
		String id;
		
		try {
			sessionCalendarController.changeSessionCalendarByDate(this.start_date.getValue());
			id = sessionController.changeSession(entry.getId(), this.title_txt.getText(),
					clasroom_dropdown.getValue(), entry.getStartAsLocalDateTime(), entry.getEndAsLocalDateTime(),
					nrOfAttendeeSpinner.getValue(), this.description_txt.getText(), this.speaker_txt.getText(),
					new ArrayList<Integer>(this.sessionMedia.keySet()), this.url_txt.getText(), this.giveClickedSessionState());
		
			this.entry.setInterval(this.start_date.getValue(), entry.getStartTime(), this.start_date.getValue(),
					entry.getEndTime());
			
			
			this.entry.setTitle(this.title_txt.getText());
			this.entry.setId(id);
			
			this.close();
		
		} catch (IllegalArgumentException e) {
			errorTxt.setText(e.getMessage());
			
			//entry.setInterval(session.getDate().atTime(session.getStartHour()), session.getDate().atTime(session.getEndHour()));
			//hier moeten de oude waarde terug opgehaald worden
		}
		catch (InformationRequiredException e) {
			//Loop over all the errors that are thrown and insert them into the error label
			String error = "";
			for(RequiredElement el: e.getInformationRequired()) {
				switch(el) {
				case ATENDEESREQUIRED:
					error += String.format("Max aantal aanwezigen niet geldig!%n");
					break;
				case CLASSROOMREQUIRED:
					error += String.format("Lokaal niet geldig!%n");
					break;
				case ENDDATEREQUIRED:
					error += String.format("Datum niet geldig!%n");
					break;
				case STARTDATEREQUIRED:
					error += String.format("Begin datum niet geldig!%n");
					break;
				case TITLEREQUIRED:
					error += String.format("Titel niet geldig!%n");
					break;
				}
			}
			errorTxt.setText(error);

			
		} catch (NotFoundException e) {
			errorTxt.setText("De sessie valt niet binnen het bereik van een sessiekalender");
		}
		
	}

	@FXML
	void pressedImageBtn(ActionEvent event) {
		// De filechooser opens to choose what image the user wants to upload. Only png files
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG images", "*.png"));

		// Prevent the popover from closing when we go to select a file
		PopOver popover = (PopOver) cancelbtn.getScene().getWindow();
		popover.autoHideProperty().set(false);

		// Create a popover to select a file (image)
		String imageURI = null;
		imageURI = fileChooser.showOpenDialog(image_btn.getScene().getWindow()).toURI().toString();
		if (imageURI != null) {
			selectedImage = new Image(imageURI);
			image_txt.setText(selectedImage.getUrl());
		}

		popover.autoHideProperty().set(true);
	}

	@FXML
	void pressedAddImageBtn(ActionEvent event) {
		if (selectedImage == null) {
			return;
		}

		addImageToScrollPane(selectedImage);
		int id = sessionController.saveImage(selectedImage);
		this.sessionMedia.put(id, selectedImage);
	}
	

    @FXML
    void pressedReminderBtn(ActionEvent event) {
    	Scene scene = new Scene(new AnnouncementController(clickedSession));
		
		Stage stage = new Stage();
		
		stage.setTitle("ITLab");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
    }
    

    @FXML
    void pressedDeleteBtn(ActionEvent event) {
    	//We first ask if the user really wants to delete the session
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Sessie verwijderen");
    	alert.setHeaderText("Deze sessie verwijderen");
    	alert.setContentText("Bent u zeker dat u deze sessie wilt verwijderen?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    //The user confirmed to delete the session
    		entry.removeFromCalendar();
    		sessionController.removeSession(clickedSession.getSessionID() + "");
        	this.close();
    	}
    }
    
    @FXML
    void classroomChanged(ActionEvent event) {
    	//If the use selected a new classroom we will set the maxSeats of that classroom to the max attendee spinner
    	if(clasroom_dropdown.getValue() != null) {
    		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, clasroom_dropdown.getValue().getMaxSeats(), clasroom_dropdown.getValue().getMaxSeats());
			nrOfAttendeeSpinner.setValueFactory(valueFactory);
    	}
    }

	private void addImageToScrollPane(Image image) {
    	//Make a new imageview. Add the chosen image to the scrollpane, so the user can see it. 
    	ImageView newImage = new ImageView(image);
    	//We set a fixed height, the width is automagically defined
    	newImage.setFitHeight(70);
    	newImage.setPreserveRatio(true);
    	
    	//We put the imageview and the delete button in an hbox so they display better
    	HBox hbox = new HBox();
    	JFXButton removeImageBtn = new JFXButton();
    	removeImageBtn.setText("X");
    	hbox.getChildren().add(newImage);
    	hbox.getChildren().add(removeImageBtn);
    	//A little bit of spacing between the images
    	image_scrollpane.setSpacing(20);
    	
    	//Event handler, removes the image when the button is clicked
    	removeImageBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//Remove the image and its key from the hashmap
            	//Firstly, we have to get the imageview that the image is in, it's the first element in the hbox 
            	ImageView imv = (ImageView) ((HBox) removeImageBtn.getParent()).getChildren().get(0);
            	//Then we get the image that's in the imageview. That image can be looked up in the hashmap and the entry can be deleted
            	Image im = imv.getImage();
            	for(Map.Entry<Integer, Image> set : sessionMedia.entrySet()){
            		if(set.getValue().equals(im)) {
            			sessionMedia.remove(set.getKey());
            		}
            	}
            	
            	//remove the hbox (imageview and button) from the scrollpane
                ((VBox) removeImageBtn.getParent().getParent()).getChildren().remove(removeImageBtn.getParent());

            }
        });
    	
    	image_scrollpane.getChildren().add(hbox);
    }
	
	private State giveClickedSessionState() {
		//This methods returns the state the current session is in.It also checks if the session was in the past
		if(clickedSession == null) {
			return State.CLOSED;
		}
		if(clickedSession.getDate().isBefore(LocalDate.now()) || (clickedSession.getEndHour().isBefore(LocalTime.now()) && clickedSession.getDate().equals(LocalDate.now()))) {
			return State.FINISHED;
		}
			
		if(sessionStateToggle.isSelected()) {
			return State.OPEN;
		}else {
			return State.CLOSED;
		}
	}
	
	private void instantiateStateToggle() {
		if(clickedSession.getDate().isBefore(LocalDate.now()) || (clickedSession.getEndHour().isBefore(LocalTime.now()) && clickedSession.getDate().equals(LocalDate.now()))) {
			this.sessionStateToggle.setDisable(true);
		}else if(clickedSession.getStateEnum() == State.FINISHED) {
			this.sessionStateToggle.setDisable(true);
		}else if(clickedSession.getStateEnum() == State.CLOSED) {
			
		}else if(clickedSession.getStateEnum() == State.OPEN) {
			this.sessionStateToggle.setDisable(true);
			this.sessionStateToggle.setText("Sessie is open");
		}
	}
	
	private void checkUserPermissions() {
		GuiUser loggedInUser = ITLabSingleton.getITLabInstance().getLoggedInUser();
		
		if(clickedSession == null) {
			//If the user just created a new session it will be null
			//Since he created the session himself we don't need to diable anything
			return;
		}
		
		//If the user is headuser he can edit every session, if he's just a responsible he can only edit his own sessions
		if(loggedInUser.getUserType().equals(UserType.RESPONSIBLE)) {
			if(!clickedSession.getHost().getUserName().equals(loggedInUser.getUserName())) {
				//If the logged in user is not the host of the selected session or he's not the head responsible he can't edit this session
				this.title_txt.setEditable(false);
				this.speaker_txt.setEditable(false);
				this.clasroom_dropdown.setEditable(false);
				this.description_txt.setEditable(false);
				this.start_date.setEditable(false);
				this.image_btn.setDisable(true);
				this.addimage_btn.setDisable(true);
				this.savebtn.setDisable(true);
				this.reminderBtn.setDisable(true);
				this.nrOfAttendeeSpinner.setEditable(false);
				this.sessionStateToggle.setDisable(true);
				this.deleteBtn.setDisable(true);
				this.errorTxt.setText("Deze sessie werd niet door u aangemaakt");
			}
		}
	}

	private void close() {
		PopOver popover = (PopOver) cancelbtn.getScene().getWindow();
		if (!entry.getId().endsWith("#"))
			entry.removeFromCalendar();
		popover.hide();
	}
}
