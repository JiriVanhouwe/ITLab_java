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


//	@ParameterizedTest
//	@MethodSource("validSessionCalendarParameters")
//	public void testCreateSessionCalendar(LocalDate startDate, LocalDate endDate) {		
//		SessionCalendar sessionCalendar = new SessionCalendar(startDate, endDate);
//		
//		Assertions.assertEquals(startDate, sessionCalendar.getStartDate());
//		Assertions.assertEquals(endDate, sessionCalendar.getEndDate());
//	}
	
	private static Stream<Arguments> validSessionCalendarParameters(){
		return Stream.of(Arguments.of(LocalDate.now().plusDays(20), LocalDate.now().plusDays(300)),
						Arguments.of(LocalDate.now().plusDays(400), LocalDate.now().plusDays(657)));
	}
	
//	@ParameterizedTest
//	@MethodSource("invalidSessionCalendarParameters")
//	public void testCreateSessionCalendar_invalidDates(LocalDate startDate, LocalDate endDate, int id) {
//		Assertions.assertThrows(IllegalArgumentException.class, () -> new SessionCalendar(startDate, endDate));
//	}
	
	private static Stream<Arguments> invalidSessionCalendarParameters(){
		//TODO: Testen of sessiekalender uniek is voor dit academiejaar, geen idee hoe dit precies moet
		return Stream.of(Arguments.of(LocalDate.now().minusDays(1), LocalDate.now().plusDays(250), 0), //Started in the past
						Arguments.of(LocalDate.now().minusDays(20), LocalDate.now().plusDays(200), 0),
						Arguments.of(LocalDate.now().plusDays(20), LocalDate.now().plusDays(19), 0), // end date is sooner than begin date
						Arguments.of(LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), 0), // start and end in the past
						Arguments.of(LocalDate.now().minusDays(1), LocalDate.now().minusDays(1), 0));//Started in the past
	}
	
//	@Test
//	public void testCreateSessionCalendar_uniqueId() {
//		SessionCalendar calendar1 = new SessionCalendar(LocalDate.now().plusDays(20), LocalDate.now().plusDays(300));
//		SessionCalendar calendar2 = new SessionCalendar(LocalDate.now().plusDays(19), LocalDate.now().plusDays(299));
//		
//		Assertions.assertNotEquals(calendar1.getId(), calendar2.getId());
//		
//	}
	
}
