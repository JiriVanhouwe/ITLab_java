package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import domain.Feedback;
import domain.GuiSession;

public class FeedbackController extends AnchorPane {

	@FXML
	private JFXButton btnGoBack;

	@FXML
	private JFXTextArea txaFeedback;

	private GuiSession selectedSession;

	public FeedbackController(GuiSession selectedSession) {
		this.selectedSession = selectedSession;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Feedback.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		loadFeedback();
	}

	private void loadFeedback() {
		List<Feedback> feedbackList = selectedSession.getFeedbackList();
		if (feedbackList.isEmpty())
			txaFeedback.setText("Er is nog geen ingezonden feedback");
		else {
			for (Feedback f : feedbackList) {
				txaFeedback.setText(f.getAuthor().getUserName() + "\n");
				txaFeedback.setText(f.getContentText() + "\n\n");
			}
		}
	}

	@FXML
	public void clickGoBack(MouseEvent event) {
		Stage stage = (Stage) getScene().getWindow();
		stage.close();
	}
}
