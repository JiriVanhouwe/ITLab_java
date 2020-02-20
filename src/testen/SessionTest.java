package testen;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import domain.Session;

class SessionTest {

	@ParameterizedTest
	@MethodSource("newSessionValidParameters")
	public void createSession_CreatesNewSession(String title, String classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee) {
		Session s = new Session(title, classroom, startDate, endDate, maxAttendee);
		Assertions.assertEquals(title, s.getTitle());
		Assertions.assertEquals(classroom, s.getClassRoom());
		Assertions.assertEquals(startDate, s.getTitle());
		Assertions.assertEquals(title, s.getTitle());
		Assertions.assertEquals(title, s.getTitle());
	}
	
	private static Stream<Arguments> newSessionValidParameters() {
		return Stream.of(Arguments.of("A new session", "B0001", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2)),
						Arguments.of("A session about ...", "B1234", LocalDateTime.now().plusDays(50), LocalDateTime.now().plusDays(50).plusMinutes(30)), //Minimal duration for a session is 30 minutes
						Arguments.of("S", "C0130", LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(1)),
						Arguments.of("Session", "C0130", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1)));
	}
	
	@ParameterizedTest
	@MethodSource("newSessionInvalidParameters")
	public void createSession_ThrowsError(String title, String classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Session(title, classroom, startDate, endDate, maxAttendee));
	}
	
	private static Stream<Arguments> newSessionInvalidParameters() {
		return Stream.of(Arguments.of(null, "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2)), //Invalid title
						Arguments.of("  ", "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2)), //Invalid title
						Arguments.of("A new session on an interesting topic", null, LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2)), //Invalid classroom
						Arguments.of("A new session on an interesting topic", "  ", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2)), //Invalid classroom
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusMinutes(29)), //Invalid enddate (duration too short)
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).minusMinutes(10)), //Invalid enddate (negative duration)s
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusDays(30).plusHours(2), LocalDateTime.now().plusDays(30)), //Invalid startdate (after enddate)
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().minusHours(2), LocalDateTime.now()), //Invalid startdate (in the past)
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusHours(12), LocalDateTime.now().plusHours(14))); //Invalid startdate (less than 1 day in advance)
	}

}
