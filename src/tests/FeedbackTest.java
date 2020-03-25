package tests;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import domain.Feedback;
import domain.User;
import domain.UserStatus;
import domain.UserType;

class FeedbackTest {
	
	@ParameterizedTest
	@MethodSource("wrongParameters") //fout in constructor gooit exceptie
	public void constructor_wrongData(User user, String message) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Feedback(user, message));
	}
	
	@ParameterizedTest
	@MethodSource("correctParameters") //correcte constructor
	public void constructor_correctData(User user, String message) {
		Feedback feedback = new Feedback(user, message);
		Assertions.assertEquals(user, feedback.getAuthor());
		Assertions.assertEquals(message, feedback.getContentText());
		
	}
	
	private static Stream<Arguments> wrongParameters(){
		User userPablo = new User("Pablo","Picasso","pablo.picasso@hogent.be",UserType.RESPONSIBLE,UserStatus.ACTIVE, "pablo");
		return Stream.of(
				Arguments.of(null, null),
				Arguments.of(null, "een bericht"),
				Arguments.of(userPablo, null),
				Arguments.of(userPablo, "    "),
				Arguments.of(userPablo, "")			
				);		
	}
	
	private static Stream<Arguments> correctParameters(){
		User userPablo = new User("Pablo","Picasso","pablo.picasso@hogent.be",UserType.RESPONSIBLE,UserStatus.ACTIVE, "pablo");
		return Stream.of(
				Arguments.of(userPablo, "Wat kan Pablo goed schilderen."),
				Arguments.of(userPablo, "Veel     spaties     kan     ook."),
				Arguments.of(userPablo, "a"),
				Arguments.of(userPablo, "12é$'--à kfaijefiapijfù$éùùµ")
				);		
	}
}
