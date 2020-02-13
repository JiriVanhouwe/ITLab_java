import java.security.SecureRandom;

public class test {
 //test commit
	//test commit Artuur
	//duizend bommen en granaten
	
	
	public static void main(String[] args) {
		System.out.println(runDezeFunctie());
	}
	
	public static String runDezeFunctie() {
		SecureRandom random = new SecureRandom();
		int score = random.nextInt(20);
		
		return (score < 10)? String.format("Je zal %d/20 behalen.%nDit is niveau 'Foulon'! Niet goed!%n", score):String.format("Je zal %d/20 behalen.%nDit is niveau 'Ramon'! Proficiat!%n", score);
	
	}
}
