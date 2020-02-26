package gui;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BeherenSessiekalenderController extends VBox {
	public BeherenSessiekalenderController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenSessiekalender.fxml"));
		
		loader.setController(this);
		loader.setRoot(this);
		
		DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node popupContent = datePickerSkin.getPopupContent();
        
        this.getChildren().add(popupContent);
		
		try{
		      loader.load();
		} 
		catch (IOException ex){
		      throw new RuntimeException(ex);
		}
	}
}
