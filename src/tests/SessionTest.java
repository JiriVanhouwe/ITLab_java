package tests;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.Session;
import domain.SessionController;
import domain.SessionRepository;

@ExtendWith(MockitoExtension.class)
class SessionTest {

	@InjectMocks
	private SessionController sc;
	
	@Mock
	private SessionRepository srDummy;
	
	
	//---UseCase: Beheren sessie ---
	@ParameterizedTest
	@MethodSource("")
	public void testChangeSession_ChangesSession(String title, LocalDateTime startDate, LocalDateTime endDate, String classRoom, int maxAttendees, String guestSpeaker) {
		//Mockito.when(srDummy);
		
		sc.changeSession(title, startDate, endDate, classRoom, maxAttendees, guestSpeaker);
		//srDummy.
	}
	
	public void testCreateSession_CreatesSession() {
		
	}
	
	@ParameterizedTest
	@MethodSource("giveSessionsParameters")
	public void testGiveSessions_GivesSessions(List<Session> sessions) {
		Mockito.when(srDummy.giveSessions()).thenReturn(sessions);
		List<Session> returnedSessions = sc.giveAllSessions();
		Assertions.assertEquals(sessions, returnedSessions);
		Mockito.verify(srDummy).giveSessions();
	}
	
	private static List<Session> giveSessionsParameters(){
		List<Session> sessions = new ArrayList<Session>();
		sessions.add(new Session("A new session", "B0001", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 10));
		sessions.add(new Session("A session about ...", "B1234", LocalDateTime.now().plusDays(50), LocalDateTime.now().plusDays(50).plusMinutes(30), 20));
		sessions.add(new Session("S", "C0130", LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(1), 30));
		sessions.add(new Session("Session", "C0130", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1), 40));
		return sessions;
	}
	
	@ParameterizedTest
	@MethodSource("giveSessionParameters")
	public void testGiveSession_GivesSession(int sessionID, Session session) {
		Mockito.when(srDummy.giveSession(sessionID)).thenReturn(session);
		Session returnedSession = sc.giveSession(sessionID);
		Assertions.assertEquals(session, returnedSession);
		//Mockito.verify(pcDummy).giveSession(sessionID);
	}
	
	private static Stream<Arguments> giveSessionParameters(){
		return Stream.of(Arguments.of("001", new Session("A new session", "B0001", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 50)),
						Arguments.of("002", new Session("A new session","B0001", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 50)),
						Arguments.of("003", new Session("A new session","B0001", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 50)),
						Arguments.of("1234", new Session("A new session","B0001", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2),  50)));
	}
	
	//---UseCase: Aanmaken sessie ---
	
	@ParameterizedTest
	@MethodSource("newSessionValidParameters")
	public void testCreateSession_CreatesNewSession(String title, String classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String guestSpeaker) {
		sc.createSession(title, startDate, endDate, classroom, maxAttendee, guestSpeaker);
		List<Session> savedSessions = srDummy.giveSessions();
		
		for(Session s : savedSessions) {
			if(s.getTitle().equals(title) && s.getStartDate().equals(startDate) && s.getClassRoom().equals(classroom)) {
				Assertions.assertTrue(true);
				return;
			}
		}
		Assertions.assertTrue(false);
	}
	

	private static Stream<Arguments> newSessionValidParameters() {
		return Stream.of(Arguments.of("A new session", "B0001", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 10, "guestspeaker"),
						Arguments.of("A session about ...", "B1234", LocalDateTime.now().plusDays(50), LocalDateTime.now().plusDays(50).plusMinutes(30), 20, "guestspeaker"), //Minimal duration for a session is 30 minutes
						Arguments.of("S", "C0130", LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(1), 30, "guestspeaker"),
						Arguments.of("Session", "C0130", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1), 40, "guestspeaker"));
	}
	
	@ParameterizedTest
	@MethodSource("newSessionInvalidParameters")
	public void testCreateSession_ThrowsError(String title, String classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String guestspeaker) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> sc.createSession(title, startDate, endDate, classroom, maxAttendee, guestspeaker));
	}
	
	private static Stream<Arguments> newSessionInvalidParameters() {
		//TODO: Cases toevoegen voor guestspeaker, bestaan geen DR over
		return Stream.of(Arguments.of(null, "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 30, "guestspeaker"), //Invalid title
						Arguments.of("  ", "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 30, "guestspeaker"), //Invalid title
						Arguments.of("A new session on an interesting topic", null, LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 30, "guestspeaker"), //Invalid classroom
						Arguments.of("A new session on an interesting topic", "  ", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 30, "guestspeaker"), //Invalid classroom
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusMinutes(29), 30, "guestspeaker"), //Invalid enddate (duration too short)
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).minusMinutes(10), 30, "guestspeaker"), //Invalid enddate (negative duration)s
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusDays(30).plusHours(2), LocalDateTime.now().plusDays(30), 30, "guestspeaker"), //Invalid startdate (after enddate)
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().minusHours(2), LocalDateTime.now(), 30, "guestspeaker"), //Invalid startdate (in the past)
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusHours(12), LocalDateTime.now().plusHours(14), 30, "guestspeaker"), //Invalid startdate (less than 1 day in advance)
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 0, "guestspeaker"), //Invalid maxAttendee (zero)
						Arguments.of("A new session on an interesting topic", "B1234", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), -1, "guestspeaker")); //Invalid maxAttendee (negative)
	}

}
