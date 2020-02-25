module ITLab {
	exports tests;
	exports domain;
	exports persistence;
	exports gui;
	exports main;

	requires javafx.base;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.graphics;
	
	requires java.persistence;
	requires java.instrument;
	requires java.sql;
	requires org.junit.jupiter.api;
	requires mockito.junit.jupiter;
	requires org.mockito;
}