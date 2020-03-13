package gui;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.controlsfx.control.PopOver;

import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import domain.Classroom;
import domain.ITLab;
import domain.ITLabSingleton;
import domain.SessionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    	
    	this.title_txt.setText(entry.getTitle());
    	this.start_date.setValue(entry.getStartDate());
    	//this.start_time.setValue(entry.getStartTime());
    	//this.end_time.setValue(entry.getEndTime());
    	//this.clasroom_dropdown.getSelectionModel().select(entry);
    	
    	fillClassrooms();
    	
    	sessionController = new SessionController(); 
    	this.entry = entry;
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
    	String id = sessionController.changeSession(entry.getId(), this.title_txt.getText(), clasroom_dropdown.getValue(), LocalDateTime.now(), LocalDateTime.now().plusHours(1), 10, this.description_txt.getText(), "");
    	
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
