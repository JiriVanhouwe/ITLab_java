package gui;

import java.io.IOException;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import domain.SessionCalendar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ManageSessionCalendarController {

	  	@FXML
	    private JFXTextField txtAcademicYear;

	    @FXML
	    private JFXDatePicker txtStartDate;

	    @FXML
	    private JFXDatePicker txtEndDate;

	    @FXML
	    private Button btnConfirm;

	    @FXML
	    private TableView<SessionCalendar> tblSessionCalendars;

	    @FXML
	    private TableColumn<SessionCalendar, String> colAcademicYear;

	    @FXML
	    private TableColumn<SessionCalendar, String> colStartEndDate;
	    
	    public ManageSessionCalendarController() {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageSessionCalendar.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			try {
				loader.load();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			
		}

}
