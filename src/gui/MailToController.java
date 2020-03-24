package gui;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import domain.GuiSession;
import domain.MailController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MailToController extends AnchorPane {
	
	@FXML
    private Label lblMessage;

	@FXML
    private Label lblInfo;
	
	@FXML
	private JFXButton btnGoBack;
	
	@FXML
	private JFXButton btnMail;

	@FXML
	private JFXTextField tfEmail;
	
	private GuiSession selectedSession;

	public MailToController(GuiSession selectedSession) {
		this.selectedSession = selectedSession;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MailTo.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		lblInfo.setText("mail info van sessie \n" + selectedSession.getTitle() + "\nmet datum " +
							selectedSession.getDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY"))
							+ " start uur " + selectedSession.getStartHour().format(DateTimeFormatter.ofPattern("HH:mm")));
	}

	private void sendMail(String title, String message, String emailadres) {
		
				lblMessage.setText("Aan het verzenden. Even geduld.");
				getScene().setCursor(javafx.scene.Cursor.WAIT);
				try {
				new MailController(title, message, emailadres);
				lblMessage.setText("Het bericht werd verzonden naar " + emailadres );
				getScene().setCursor(javafx.scene.Cursor.DEFAULT);
				} catch (IllegalArgumentException e) {
					lblMessage.setText(e.getMessage());
				}
				getScene().setCursor(javafx.scene.Cursor.WAIT);
			
		}	
	
	@FXML
	public void clickGoBack(MouseEvent event) {
		Stage stage = (Stage) getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void clickMail(MouseEvent event) {
		String title = "Lijst aanwezigen van Sessie itlab";
		String infoSession = String.format(selectedSession.getTitle() + 
				"\nmet datum " + selectedSession.getDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")) + " start uur " + selectedSession.getStartHour().format(DateTimeFormatter.ofPattern("HH:mm")));
		String message = String.format("Beste%n%nDit waren de aanwezigen voor de sessie %s%n  %s%n%nVriendelijke groeten uit het ITLab", 
				infoSession, 
				selectedSession.getAttendees().stream().map(e -> String.format("   %s %s", e.getFirstName(), e.getLastName())).collect(Collectors.joining("\n"))); 
		String emailadres = tfEmail.getText();
		sendMail(title, message, emailadres);
		
	}
	
	
}
