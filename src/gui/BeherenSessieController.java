package gui;

import java.io.IOException;

import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXTimePicker;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BeherenSessieController extends VBox{

    @FXML
    private TextField titel_txtfld;

    @FXML
    private TextArea description_txtfld;

    @FXML
    private TextField image_txtfld;

    @FXML
    private Button filechooser_btn;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Button edit_btn;

    @FXML
    private Button cancel_btn;
    
    
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
    	
    	this.titel_txtfld.setText(entry.getTitle());
    	this.datepicker.setValue(entry.getStartDate());
    	//this.timepicker.setValue(entry.getStartTime());
    	System.out.println(entry.getId());
    }
}
