package tests;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import domain.SessionCalendar;

class SessionCalendarTest {


	@Test
	public void testCreatieSessionCalendar() {
		LocalDate startDate = LocalDate.of(2019, 9, 1);
		LocalDate endDate = LocalDate.of(2020, 6, 30);		
		
		SessionCalendar sessionCalendar = new SessionCalendar(startDate, endDate);
		
		Assertions.assertEquals(startDate, sessionCalendar.getStartDate());
		Assertions.assertEquals(endDate, sessionCalendar.getEndDate());
	}
	
	@ParameterizedTest
	public void testCreatieSessionCalendar_ongeldigeDatums() {
		
	}
	
}
