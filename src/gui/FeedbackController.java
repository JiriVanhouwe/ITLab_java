package gui;


import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import domain.Feedback;
import domain.GuiFeedback;
import domain.GuiSession;
import domain.SessionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FeedbackController extends AnchorPane{

	
	private GuiSession selectedSession;
	
	private SessionController sessionController;

	@FXML
	private JFXButton btnGoBack;

	@FXML
	private JFXTextArea txaFeedback;

	@FXML
	private JFXButton btnDeleteFeedback;
	
	@FXML
	private ChoiceBox<GuiFeedback> cb_feedbackfordelete;
	
	private ObservableList<GuiFeedback> feedbackList;
	
	private GuiFeedback selectedFeedback;
	
	public FeedbackController(GuiSession selectedSession,SessionController sessionController) {
		this.sessionController = sessionController;
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
		
		cb_feedbackfordelete.getSelectionModel().selectedItemProperty().addListener((obsVal, oldVal, newVal) -> {
			if (newVal != null) {
				this.selectedFeedback = newVal;
			}
		});
		
	}

	private void loadFeedback() {
		feedbackList = FXCollections.observableArrayList(selectedSession.getFeedbackList());
		//textview vullen
		txaFeedback.setText("");
		if (feedbackList.isEmpty()) {
			txaFeedback.setText("Er is nog geen ingezonden feedback");
			cb_feedbackfordelete.setDisable(true);
			btnDeleteFeedback.setDisable(true);
		}
		else {
			
			for (GuiFeedback f : feedbackList) {
				txaFeedback.appendText("Feedback met nummer: " + f.getId()+"\n" + "van "+f.getAuthor().getUserName() + "\n");
				txaFeedback.appendText(f.getContentText() + "\n\n");
			}
			// choicebox vullen
			cb_feedbackfordelete.setItems(feedbackList);
		}
		
	}
	
    @FXML
    void clickDeleteSelectedFeedback(MouseEvent event) {  
    	this.sessionController.removeFeedbackFromSession(this.selectedFeedback,this.selectedSession);
    	loadFeedback();
    }
	

	@FXML
	public void clickGoBack(MouseEvent event) {
		Stage stage = (Stage) getScene().getWindow();
		stage.close();
	}
}
