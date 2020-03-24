package gui;

import java.io.IOException;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import domain.GuiSession;
import domain.MailController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AnnouncementController extends AnchorPane{
	
    @FXML
    private Button btnSend;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblMessage;

    @FXML
    private JFXTextField txfTitle;

    @FXML
    private Button btnGoBack;

    @FXML
    private Label lblSession;

    @FXML
    private JFXTextArea txaMessage;

    @FXML
    private Label lblSessionInfo;
    
    private GuiSession session;
    private String title;
    private String messageToSend;
	
	public AnnouncementController(GuiSession session) {
		this.session = session;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Announcement.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex); 
		}
		//TODO stel lblSession en lblSessionInfo in adhv de meegegeven sessie
		lblSession.setText(session.getTitle());
		lblSessionInfo.setText(String.format("Spreker: %s op %s van %s tot %s", session.getNameGuest(), session.getDate(), session.getStartHour(), session.getEndHour()));
	}	
	
    @FXML
    void clickGoBack(MouseEvent event) {
    	Stage stage = (Stage) getScene().getWindow();
    	stage.close();
    }

    @FXML
    void clickSend(MouseEvent event) {
    	title = txfTitle.getText();
    	messageToSend = txaMessage.getText();
    	
    	if(title.isBlank())
    		lblMessage.setText("Gelieve een titel op te geven.");
    	if(messageToSend.isBlank())
    		lblMessage.setText("Gelieve een bericht op te geven.");
    	
    	if(!title.isBlank() && !messageToSend.isBlank())
    	{
    		sendMail(title, messageToSend);
    	}
    	
    }

	private void sendMail(String title, String message) {
		if(session.getRegisteredUsers() == null || session.getRegisteredUsers().isEmpty()) {			
				lblMessage.setText("Er zijn nog geen geregistreerde gebruikers voor deze sessie.");
		}
		else {
				lblMessage.setText("Aan het verzenden. Even geduld.");
				getScene().setCursor(javafx.scene.Cursor.WAIT);
				new MailController(title, message, session.getRegisteredUsers());
				lblMessage.setText("Het bericht werd verzonden naar de geregistreerde studenten.");
				getScene().setCursor(javafx.scene.Cursor.DEFAULT);
			}
		}	
	}

