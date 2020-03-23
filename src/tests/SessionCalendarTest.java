package tests;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import domain.SessionCalendar;

class SessionCalendarTest {


	@ParameterizedTest
	@MethodSource("validSessionCalendarParameters")
	public void testCreateSessionCalendar(int id, LocalDate startDate, LocalDate endDate) {		
		SessionCalendar sessionCalendar = new SessionCalendar(id, startDate, endDate);
		
		Assertions.assertEquals(startDate, sessionCalendar.getStartDate());
		Assertions.assertEquals(endDate, sessionCalendar.getEndDate());
	}
	
	private static Stream<Arguments> validSessionCalendarParameters(){
		return Stream.of(Arguments.of(20192020,LocalDate.now().plusDays(20), LocalDate.now().plusDays(300)),
						Arguments.of(20192020,LocalDate.now().plusDays(400), LocalDate.now().plusDays(657)));
	}
	
	@ParameterizedTest
	@MethodSource("invalidSessionCalendarParameters")
	public void testCreateSessionCalendar_invalidDates(int  id, LocalDate startDate, LocalDate endDate) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new SessionCalendar(id, startDate, endDate));
	}
	
	private static Stream<Arguments> invalidSessionCalendarParameters(){
		//TODO: Testen of sessiekalender uniek is voor dit academiejaar, geen idee hoe dit precies moet
		return Stream.of(Arguments.of(20192020,LocalDate.now().minusDays(1), LocalDate.now().plusDays(250), 0), //Started in the past
						Arguments.of(20192020,LocalDate.now().minusDays(20), LocalDate.now().plusDays(200), 0),
						Arguments.of(20192020,LocalDate.now().plusDays(20), LocalDate.now().plusDays(19), 0), // end date is sooner than begin date
						Arguments.of(20192020,LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), 0), // start and end in the past
						Arguments.of(20192020,LocalDate.now().minusDays(1), LocalDate.now().minusDays(1), 0),//Started in the past
						Arguments.of(20172020,LocalDate.now().plusDays(400), LocalDate.now().plusDays(657)));//invalid id
	}
	

}
