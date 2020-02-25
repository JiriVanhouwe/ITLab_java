package tests;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import domain.SessionCalendar;

class SessionCalendarTest {


	@Test
	public void testCreatieSessionCalendar() {
		LocalDateTime startDate = LocalDateTime.of(2019, 9, 1);
		LocalDateTime endDate = LocalDateTime.of(2020, 6, 30);		
		
		SessionCalendar sessionCalendar = new SessionCalendar(startDate, endDate);
		
		Assertions.assertEquals(startDate, sessionCalendar.getStartDate());
		Assertions.assertEquals(endDate, sessionCalendar.getEndDate());
	}
	
	@ParameterizedTest
	public void testCreatieSessionCalendar_ongeldigeDatums() {
		
	}
	
}
