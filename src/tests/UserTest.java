package tests;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import domain.User;
import domain.UserStatus;
import domain.UserType;

public class UserTest {

	@ParameterizedTest
	@MethodSource("wrongParameters") //fout in constructor
	public void constructor_wrongData(String[] data, UserType userType, UserStatus userStatus, String password) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new User(data[0], data[1], data[2], userType, userStatus, password));
	}
	
	@ParameterizedTest
	@MethodSource("correctParameters")//goede constructor
	public void constructor_correctData(String[] data, UserType userType, UserStatus userStatus, String password) {
		User user = new User(data[0], data[1], data[2], userType, userStatus, password);
		Assertions.assertEquals(user.getFirstName(), data[0]);
		Assertions.assertEquals(user.getLastName(), data[1]);
		Assertions.assertEquals(user.getUserName(), data[2]);
		Assertions.assertEquals(user.getUserType(), userType);
		Assertions.assertEquals(user.getUserStatus(), userStatus);
		}
	
	private static Stream<Arguments> wrongParameters(){
		return Stream.of(
				//fout in voornaam
				Arguments.of(new String[] {"Don4ld", "Troemp", "donald.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Don)-)ld", "Troemp", "donald.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"1234", "Troemp", "donald.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"$-)à", "Troemp", "donald.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"   ", "Troemp", "donald.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {null, "Troemp", "donald.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				//fout in familienaam
				Arguments.of(new String[] {"Donald", "Tr09mp", "donald.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Tro09$-)mp", "donald.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "8976", "donald.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "!ùù=:", "don4ld.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "   ", "don4ld.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", null, "donald.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				//fout in emailadres
				Arguments.of(new String[] {"Donald", "Troemp", "barack.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.obama@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "3mepd.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "234.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "$ù-))à.troemp@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.098@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.µ$ù=:@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.09ù^$µ@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@blablabla"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troempstudent.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@gmail.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@123.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@$^^ùd.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@student.hogent"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "    "}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", null}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				//fout in een van de enums
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@hogent.be"}, null, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@student.hogent.be"}, UserType.USERITLAB, null, "123")
				);		
	}
	
	private static Stream<Arguments> correctParameters(){
		return Stream.of(
				Arguments.of(new String[] {"Rem", "Brand", "rem.brand@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"rem", "Brand", "rem.brand@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Rem", "brand", "rem.brand@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"rem", "brand", "rem.brand@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Rem", "Brand", "REM.brand@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Rem", "Brand", "rem.BRAND@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Rem", "Brand", "REM.BRAND@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Rem", "Brand", "rem.brand@hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Rem", "Brand", "rem.brand@hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"REM", "Brand", "rem.brand@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123"),
				Arguments.of(new String[] {"Rem", "BRAND", "rem.brand@student.hogent.be"}, UserType.USERITLAB, UserStatus.ACTIVE, "123")
				);		
	}
}
