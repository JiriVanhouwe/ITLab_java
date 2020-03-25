open module ITLab {
	exports tests;
	exports domain;
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
	requires org.junit.jupiter.params;
	requires com.calendarfx.view;
	requires org.controlsfx.controls;
	requires com.jfoenix;
	requires java.desktop;
	requires java.mail;
}