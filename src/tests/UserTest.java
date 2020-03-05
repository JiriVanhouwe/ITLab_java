package tests;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domain.User;
import domain.UserStatus;
import domain.UserType;

public class UserTest {
	
	//	public User(String firstName, String lastName, String userName, UserType userType, UserStatus userStatus) {

	@ParameterizedTest
	@MethodSource("wrongAttributes")
	public void constructor_wrongData(String[] data, UserType userType, UserStatus userStatus) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new User(data[0], data[1], data[2], userType, userStatus));
	}
	
	private static Stream<Arguments> wrongAttributes(){
		return Stream.of(
				//fout in voornaam
				Arguments.of(new String[] {"Don4ld", "Troemp", "donald.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Don)-)ld", "Troemp", "donald.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"1234", "Troemp", "donald.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"$-)à", "Troemp", "donald.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"   ", "Troemp", "donald.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {null, "Troemp", "donald.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				//fout in familienaam
				Arguments.of(new String[] {"Donald", "Tr09mp", "donald.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Tro09$-)mp", "donald.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "8976", "donald.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "!ùù=:", "don4ld.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "   ", "don4ld.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", null, "donald.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				//fout in emailadres
				Arguments.of(new String[] {"Donald", "Troemp", "barack.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.obama@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "3mepd.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "234.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "$ù-))à.troemp@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.098@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.µ$ù=:@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.09ù^$µ@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@blablabla"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troempstudent.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@gmail.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@123.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@$^^ùd.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@student.hogent"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "    "}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", null}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				//fout in een van de enums
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@student.hogent.turkey"}, null, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Donald", "Troemp", "donald.troemp@student.hogent.turkey"}, UserType.GEBRUIKER, null)
				);		
	}
	
	private static Stream<Arguments> correctAttributes(){
		return Stream.of(
				Arguments.of(new String[] {"Rem", "Brand", "rem.brand@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"rem", "Brand", "rem.brand@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Rem", "brand", "rem.brand@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"rem", "brand", "rem.brand@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Rem", "Brand", "REM.brand@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Rem", "Brand", "rem.BRAND@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Rem", "Brand", "REM.BRAND@STUDENT.HOGENT.COM"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Rem", "Brand", "rem.brand@hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Rem", "Brand", "rem.brand@HOGENT.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"REM", "Brand", "rem.brand@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF),
				Arguments.of(new String[] {"Rem", "BRAND", "rem.brand@student.hogent.com"}, UserType.GEBRUIKER, UserStatus.ACTIEF)
				);		
	}
	
	//incorrecte firstname
	//incorrecte lastname
	//incorrecte username
	//lege userType
	//lege userStatus
}
