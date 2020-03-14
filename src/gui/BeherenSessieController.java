package gui;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.controlsfx.control.PopOver;

import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import domain.Classroom;
import domain.ITLab;
import domain.ITLabSingleton;
import domain.Session;
import domain.SessionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BeherenSessieController extends VBox{

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
    private JFXButton savebtn;

    @FXML
    private JFXButton cancelbtn;
    
    @FXML
    private JFXButton image_btn;
    
    private ITLab iTLab;
    
    private Entry entry;
    
    private SessionController sessionController;
    
    public BeherenSessieController(Entry entry) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenSessie.fxml"));
    	loader.setController(this);
    	loader.setRoot(this);
    	
    	try{
    	      loader.load();
    	} 
    	catch (IOException ex){
    	      throw new RuntimeException(ex);
    	}
    	
    	sessionController = new SessionController(); 
    	this.entry = entry;
    	fillClassrooms();
    	
    	Session clickedSession = sessionController.giveSession(entry.getId());
    	this.title_txt.setText(entry.getTitle());
    	this.start_date.setValue(entry.getStartDate());
    	if(clickedSession != null || entry.getId().charAt(entry.getId().length() - 1) == '#') {
    		this.description_txt.setText(clickedSession.getDescription());
        	this.clasroom_dropdown.getSelectionModel().select(clickedSession.getClassroom());
        	this.speaker_txt.setText(clickedSession.getNameGuest());
    	}
    	
    	//Formatting for the time
    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    	this.fromHour_txt.setText(entry.getStartTime().format(timeFormat).toString());
    	this.toHour_txt.setText(entry.getEndTime().format(timeFormat).toString());
    	
    	
    }
    
    private void fillClassrooms() {
		ObservableList<Classroom> classrooms = FXCollections.observableArrayList(ITLabSingleton.getITLabInstance().getClassrooms());
		clasroom_dropdown.getItems().addAll(classrooms);
	}

	@FXML
    void pressedCancelBtn(ActionEvent event) {
    	this.close();
    }

    @FXML
    void pressedSaveBtn(ActionEvent event) {
    	String id = sessionController.changeSession(entry.getId(), this.title_txt.getText(), clasroom_dropdown.getValue(), entry.getStartAsLocalDateTime(), entry.getEndAsLocalDateTime(), clasroom_dropdown.getValue().getMaxSeats(), this.description_txt.getText(), this.speaker_txt.getText());
    	this.entry.setInterval(this.start_date.getValue(), entry.getStartTime(), this.start_date.getValue(), entry.getEndTime());
    	this.entry.setTitle(this.title_txt.getText());
    	this.entry.setId(id);
    	this.close();
    }
    
    @FXML
    void pressedImageBtn(ActionEvent event) {
    	//De filechooser opent om bestanden te kiezen
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(
    		     new FileChooser.ExtensionFilter("PNG images", "*.png")
    		    ,new FileChooser.ExtensionFilter("JPEG images", "*.jpeg")
    		);
    	File selectedFile = fileChooser.showOpenDialog(image_btn.getScene().getWindow());
    	
    	//Dit bestand moeten we nu ergens opslaan
    }
    
    private void close() {
    	PopOver popover = (PopOver)cancelbtn.getScene().getWindow();
    	if(!entry.getId().endsWith("#"))
    		entry.removeFromCalendar();
    	popover.hide();
    }
}
