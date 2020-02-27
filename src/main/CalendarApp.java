package main;

import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarApp extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {

            CalendarView calendarView = new CalendarView(); 

                Calendar calendar1 = new Calendar("Kalender 1"); 
                Calendar calendar2 = new Calendar("Kalender 2");
                
                

                calendar1.setStyle(Style.STYLE1); 
                calendar2.setStyle(Style.STYLE2);

                CalendarSource myCalendarSource = new CalendarSource("My Calendars"); 
                myCalendarSource.getCalendars().addAll(calendar1, calendar2);

                calendarView.getCalendarSources().addAll(myCalendarSource); 

                calendarView.setRequestedTime(LocalTime.now());

                Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
                        @Override
                        public void run() {
                                while (true) {
                                        Platform.runLater(() -> {
                                                calendarView.setToday(LocalDate.now());
                                                calendarView.setTime(LocalTime.now());
                                        });

                                        try {
                                                // update every 10 seconds
                                                sleep(10000);
                                        } catch (InterruptedException e) {
                                                e.printStackTrace();
                                        }

                                }
                        };
                };
                
                Entry<String> session1 = new Entry<>("Sessie 1");
                session1.changeStartDate(LocalDate.now().plusDays(1));
                session1.changeStartTime(LocalTime.now().plusHours(5));
                session1.changeStartTime(LocalTime.now().plusHours(10));
                session1.changeEndDate(LocalDate.now().plusDays(1));
                calendar1.addEntry(session1);

                updateTimeThread.setPriority(Thread.MIN_PRIORITY);
                updateTimeThread.setDaemon(true);
                updateTimeThread.start();

                Scene scene = new Scene(calendarView);
                primaryStage.setTitle("Calendar");
                primaryStage.setScene(scene);
                primaryStage.setWidth(1300);
                primaryStage.setHeight(1000);
                primaryStage.centerOnScreen();
                primaryStage.show();
                
                primaryStage.setMaximized(true);
        }

        public static void main(String[] args) {
                launch(args);
        }
}