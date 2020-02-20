package tests;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.DomainController;
import domain.Session;
import persistence.PersistenceController;

@ExtendWith(MockitoExtension.class)
class SessionTest {

	@InjectMocks
	private DomainController dc;
	
	@Mock
	private PersistenceController pcDummy;
	
	/*@Before
	public void before() {
		this.dc = new DomainController();
	}*/
	
	//---UseCase: Beheren sessie ---
	
	@ParameterizedTest
	@CsvSource("")
	public void giveSession_GivesSessions() {
		//Mockito.when(pcDummy.)
		
		//List<Session> sessions = dc.giveSessions();
		
	}
	
	//---UseCase: Aanmaken sessie ---
	
	@ParameterizedTest
	@MethodSource("newSessionValidParameters")
	public void createSession_CreatesNewSession(String title, String classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee) {
		//TODO: Dit moet nog veranderd worden naar dc.createSession() denk ik. Best eens bespreken. (Moeilijk om te controleren, aangezien de methode in de dc niets retourneert)
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
	public void createSession_ThrowsError(String title, String classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String guestspeaker) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> dc.createSession(title, startDate, endDate, classroom, maxAttendee, guestspeaker));
	}
	
	private static Stream<Arguments> newSessionInvalidParameters() {
		//TODO: Cases toevoegen voor guestspeaker, bestaan geen DR over
		return Stream.of(Arguments.of(null, "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), "guestspeaker"), //Invalid title
						Arguments.of("  ", "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), "guestspeaker"), //Invalid title
						Arguments.of("A new session on an interesting topic", null, LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), "guestspeaker"), //Invalid classroom
						Arguments.of("A new session on an interesting topic", "  ", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), "guestspeaker"), //Invalid classroom
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusMinutes(29), "guestspeaker"), //Invalid enddate (duration too short)
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).minusMinutes(10), "guestspeaker"), //Invalid enddate (negative duration)s
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusDays(30).plusHours(2), LocalDateTime.now().plusDays(30), "guestspeaker"), //Invalid startdate (after enddate)
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().minusHours(2), LocalDateTime.now(), "guestspeaker"), //Invalid startdate (in the past)
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusHours(12), LocalDateTime.now().plusHours(14), "guestspeaker")); //Invalid startdate (less than 1 day in advance)
	}

}
