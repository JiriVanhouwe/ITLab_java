package main;

import java.time.LocalDateTime;
import java.util.Locale.Category;

import domain.Campus;
import domain.ClassRoomCategory;
import domain.Classroom;
import domain.ITLab;
import domain.Session;
import domain.UserController;
import gui.LogInController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class StartUpGui extends Application {

	@Override
	public void start(Stage stage) {
		ITLab itlab = new ITLab();
		UserController usercontroller = new UserController(itlab);
		
		Scene scene = new Scene(new LogInController(usercontroller));
		stage.setTitle("Log in");
		stage.setScene(scene);

		// The stage will not get smaller than its preferred (initial) size.
		stage.setOnShown((WindowEvent t) -> {
			stage.setMinWidth(stage.getWidth());
			stage.setMinHeight(stage.getHeight());
		});
		
		Classroom cr = new Classroom("ITLAB", Campus.GENT, 30, ClassRoomCategory.ITLAB);
		
		Session session = new Session("title", cr, LocalDateTime.now(), LocalDateTime.now().plusDays(2), 5);
		Session session2 = new Session("title", cr, LocalDateTime.now(), LocalDateTime.now().plusDays(2), 5);
		System.out.println(session.getSessionID());
		System.out.println(session2.getSessionID());
		
		//stage.initStyle(StageStyle.UNDECORATED); // heel mooi effect, maar we moeten er nog in slagen het op het hoofdscherm terug te veranderen naar DECORATED
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
