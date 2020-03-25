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
import domain.SessionBuilder;
import domain.SessionController;
import domain.User;
import domain.UserStatus;
import domain.UserType;
import exceptions.InformationRequiredException;
 class SessionTest {

	private SessionBuilder sb;
	private User dummyUser;
	
	@BeforeEach
	public void before() {
		this.sb = new SessionBuilder();
		this.dummyUser = new User("user","test","test.user@hogent.be",UserType.RESPONSIBLE,UserStatus.ACTIVE, "password");
	}

	
	@ParameterizedTest
	@MethodSource("newSessionValidParameters")
	public void testCreateSession_CreatesNewSession(String title, String description, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee,  Classroom classroom, String nameGuest) throws InformationRequiredException {
		sb.createSession();
		sb.buildTitle(title);
		sb.buildDates(startDate, endDate);
		sb.buildClassroomAndMaxAtendeees(classroom , maxAttendee);
		sb.buildDescription(description);
		sb.buildGuestSpeaker(nameGuest);
		
		sb.buildHost(this.dummyUser);
		
		assertEquals(sb.getSession().getClass(), Session.class);
	}
	

	private static Stream<Arguments> newSessionValidParameters() {
		return Stream.of(Arguments.of("A new session", "some text", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 10,new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), "guestspeaker"),
						Arguments.of("A session about ...", "some text", LocalDateTime.now().plusDays(50), LocalDateTime.now().plusDays(50).plusMinutes(30), 20,new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB),"guestspeaker"), //Minimal duration for a session is 30 minutes
						Arguments.of("S", "some text", LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(1), 30, new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB),"guestspeaker"),
						Arguments.of("Session", "some text", LocalDateTime.now().plusDays(1).plusMinutes(1), LocalDateTime.now().plusDays(1).plusHours(1), 40,new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB),"guestspeaker"));
	}
	
	@ParameterizedTest
	@MethodSource("newSessionInvalidParameters")
	public void testCreateSession_ThrowsError(String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String guestspeaker, String description) {
		sb.createSession();
		sb.buildTitle(title);
		sb.buildDates(startDate, endDate);
		sb.buildClassroomAndMaxAtendeees(classroom , maxAttendee);
		sb.buildDescription(description);
		sb.buildGuestSpeaker(guestspeaker);
		
		sb.buildHost(this.dummyUser);
		
		
		Assertions.assertThrows(InformationRequiredException.class, () -> {
			sb.getSession();
		});
	}
	
	private static Stream<Arguments> newSessionInvalidParameters() {
		//TODO: Cases toevoegen voor guestspeaker, bestaan geen DR over
		return Stream.of(
						Arguments.of("  ", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 30, "guestspeaker","uitleg over sessie"), //Invalid title
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 51, "guestspeaker","uitleg over sessie"), //Invalid maxAttendee (higer then maxseats)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusMinutes(29), 30, "guestspeaker","uitleg over sessie"), //Invalid enddate (duration too short)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).minusMinutes(10), 30, "guestspeaker","uitleg over sessie"), //Invalid enddate (negative duration)s
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30).plusHours(2), LocalDateTime.now().plusDays(30), 30, "guestspeaker","uitleg over sessie"), //Invalid startdate (after enddate)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().minusHours(2), LocalDateTime.now(), 30, "guestspeaker","uitleg over sessie"), //Invalid startdate (in the past)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusHours(12), LocalDateTime.now().plusHours(14), 30, "guestspeaker","uitleg over sessie"), //Invalid startdate (less than 1 day in advance)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 0, "guestspeaker","uitleg over sessie"), //Invalid maxAttendee (zero)
						Arguments.of("A new session on an interesting topic", new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), -1, "guestspeaker","uitleg over sessie")); //Invalid maxAttendee (negative)
	}
	
	@ParameterizedTest
	@MethodSource("newSessionInvalidParameters")
	public void testChangesSession_ThrowsError(String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String guestspeaker, String description) throws InformationRequiredException {
		Session s = giveValidSession("A new session", "some text", LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(2), 10,new Classroom("testClassroom", Campus.GENT, 50, ClassRoomCategory.ITLAB), "guestspeaker");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			s.changeSession(title, classroom, startDate, endDate, maxAttendee, description, guestspeaker, s.getMedia(), s.getVideoURL(), s.getHost(), s.getStateEnum());
		});
		
	}
	
	
	public Session giveValidSession(String title, String description, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee,  Classroom classroom, String nameGuest) throws InformationRequiredException {
		sb.createSession();
		sb.buildTitle(title);
		sb.buildDates(startDate, endDate);
		sb.buildClassroomAndMaxAtendeees(classroom , maxAttendee);
		sb.buildDescription(description);
		sb.buildGuestSpeaker(nameGuest);
		
		
		sb.buildHost(this.dummyUser);
		
		return sb.getSession();
	}

}
