package gui;

import java.io.File;
import java.io.IOException;

import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import domain.Classroom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

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
    	//this.clasroom_dropdown.getItems().add(new Classroom("B1234", Campus.GENT, 200, ClassRoomCategory.CLASSROOM));
    	System.out.println(entry.getId());
    }
    
    @FXML
    void pressedCancelBtn(ActionEvent event) {
    	
    }

    @FXML
    void pressedSaveBtn(ActionEvent event) {

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
}
