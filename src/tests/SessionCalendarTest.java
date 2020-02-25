package tests;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	public void testCreateSessionCalendar(LocalDateTime startDate, LocalDateTime endDate) {		
		SessionCalendar sessionCalendar = new SessionCalendar(startDate, endDate);
		
		Assertions.assertEquals(startDate, sessionCalendar.getStartDate());
		Assertions.assertEquals(endDate, sessionCalendar.getEndDate());
	}
	
	private static Stream<Arguments> validSessionCalendarParameters(){
		return Stream.of(Arguments.of(LocalDateTime.now().plusDays(20), LocalDateTime.now().plusDays(300)),
						Arguments.of(LocalDateTime.now().plusDays(400), LocalDateTime.now().plusDays(657)));
	}
	
	@ParameterizedTest
	public void testCreateSessionCalendar_ongeldigeDatums() {
		
	}
	
}
