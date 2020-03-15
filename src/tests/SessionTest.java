package tests;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.Campus;
import domain.ClassRoomCategory;
import domain.Classroom;
import domain.Session;
import domain.SessionController;

public class SessionTest {

	private SessionController sc;
	
//	@BeforeEach
//	public void before() {
//		this.sc = new SessionController();
//	}
	
	//---UseCase: Beheren sessie ---
	@ParameterizedTest
	@MethodSource("newSessionValidParameters")
	public void testChangeSession_ChangesSessionValid(String title, String description, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee,  Classroom classroom, String nameGuest) {
		Session s = new Session("A new session", "some text", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 
										10,new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), "guestspeaker", null);
		
		s.changeSession(title, classroom, startDate, endDate, maxAttendee, description, nameGuest);
		
		assertEquals(title,s.getTitle());
		assertEquals(classroom, s.getClassroom());
	}

	@ParameterizedTest
	@MethodSource("newSessionInvalidParameters")
	public void testChangeSession_ChangesSessionInvalid(String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String guestspeaker, String description) {
		Session s = new Session("A new session", "some text", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 
				10,new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), "guestspeaker");
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> s.changeSession(title, classroom, startDate, endDate, maxAttendee, description, guestspeaker));
	
	}
	
//	@ParameterizedTest
//	@MethodSource("giveSessionsParameters")
//	public void testGiveSessions_GivesSessions(List<Session> sessions) {
//	
//		List<Session> returnedSessions = sc.giveAllSessions();
//		Assertions.assertEquals(sessions, returnedSessions);
//		
//	}
//	
//	private static List<Session> giveSessionsParameters(){
//		List<Session> sessions = new ArrayList<Session>();
//		sessions.add(new Session("A new session", "B0001", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 10, null, null));
//		sessions.add(new Session("A session about ...", "B1234", LocalDateTime.now().plusDays(50), LocalDateTime.now().plusDays(50).plusMinutes(30), 20, null, null));
//		sessions.add(new Session("S", "C0130", LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(1), 30, null, null));
//		sessions.add(new Session("Session", "C0130", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1), 40, null, null));
//		return sessions;
//	}
//	
//	@ParameterizedTest
//	@MethodSource("giveSessionParameters")
//	public void testGiveSession_GivesSession(int sessionID, Session session) {
//		Mockito.when(srDummy.giveSession(sessionID)).thenReturn(session);
//		Session returnedSession = sc.giveSession(sessionID);
//		Assertions.assertEquals(session, returnedSession);
//		//Mockito.verify(pcDummy).giveSession(sessionID);
//	}
//	
//	private static Stream<Arguments> giveSessionParameters(){
//		return Stream.of(Arguments.of("001", new Session("A new session", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 50)),
//						Arguments.of("002", new Session("A new session",new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 50)),
//						Arguments.of("003", new Session("A new session",new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 50)),
//						Arguments.of("1234", new Session("A new session",new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2),  50)));
//	}
//	
	//---UseCase: Aanmaken sessie ---
	
	@ParameterizedTest
	@MethodSource("newSessionValidParameters")
	public void testCreateSession_CreatesNewSession(String title, String description, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee,  Classroom classRoom, String nameGuest) {
		Session s = new Session(title, description, startDate, endDate, maxAttendee, classRoom, nameGuest);
		assertEquals(s.getClass(), Session.class);
	}
	

	private static Stream<Arguments> newSessionValidParameters() {
		return Stream.of(Arguments.of("A new session", "some text", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 10,new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), "guestspeaker"),
						Arguments.of("A session about ...", "some text", LocalDateTime.now().plusDays(50), LocalDateTime.now().plusDays(50).plusMinutes(30), 20,new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB),"guestspeaker"), //Minimal duration for a session is 30 minutes
						Arguments.of("S", "some text", LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(1), 30, new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB),"guestspeaker"),
						Arguments.of("Session", "some text", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1), 40,new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB),"guestspeaker"));
	}
	
	@ParameterizedTest
	@MethodSource("newSessionInvalidParameters")
	public void testCreateSession_ThrowsError(String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String guestspeaker, String description) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> 
				new Session(title, description, startDate, endDate, maxAttendee, classroom, guestspeaker));
	}
	
	private static Stream<Arguments> newSessionInvalidParameters() {
		//TODO: Cases toevoegen voor guestspeaker, bestaan geen DR over
		return Stream.of(Arguments.of(null, new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 30, "guestspeaker","uitleg over sessie"), //Invalid title
						Arguments.of("  ", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 30, "guestspeaker","uitleg over sessie"), //Invalid title
						Arguments.of("A new session on an interesting topic", null, LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 30, "guestspeaker","uitleg over sessie"), //Invalid classroom
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 51, "guestspeaker","uitleg over sessie"), //Invalid maxAttendee (higer then maxseats)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusMinutes(29), 30, "guestspeaker","uitleg over sessie"), //Invalid enddate (duration too short)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).minusMinutes(10), 30, "guestspeaker","uitleg over sessie"), //Invalid enddate (negative duration)s
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30).plusHours(2), LocalDateTime.now().plusDays(30), 30, "guestspeaker","uitleg over sessie"), //Invalid startdate (after enddate)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().minusHours(2), LocalDateTime.now(), 30, "guestspeaker","uitleg over sessie"), //Invalid startdate (in the past)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusHours(12), LocalDateTime.now().plusHours(14), 30, "guestspeaker","uitleg over sessie"), //Invalid startdate (less than 1 day in advance)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 0, "guestspeaker","uitleg over sessie"), //Invalid maxAttendee (zero)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), -1, "guestspeaker","uitleg over sessie")); //Invalid maxAttendee (negative)
	}

}
