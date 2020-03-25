package domain;

import java.security.SecureRandom;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailController {
	// voorlopig worden er enkele mails van dit adres verzonden.
	// dit zou het adres van de hoofdverantwoordelijke kunnen zijn.
	// https://support.google.com/accounts/answer/6010255?hl=en hier moet de less
	// secure apps AAN staan.
	private final String from = "hetitlab@gmail.com";
	private final String password = "admin2020";

	private Session session;

	public MailController(User user) { //voor passwoord te wijzigen, is een standaard e-mail
		prepareToEmail();
		sendPasswordEmail(user);
	}
	
	public MailController(String title, String message, List<User> users) { //als je meerdere mensen tegelijk wil sturen, hier geef je zelf de boodschap en titel op
		prepareToEmail();
		sendEmailToMultipleReceivers(title, message, users);
	}

	public MailController(String title, String message, String email) {
		isValidEmailAddress(email);
		prepareToEmail();
		sendEmailToOneReceiverWithMailAdres(title, message, email);
	}

	private void prepareToEmail() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", 587);

		this.session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
	}

	private void sendEmailToMultipleReceivers(String title, String message, List<User> users) {
		users.forEach(user -> // het bericht sturen naar alle adressen in de lijst
		{
			try {
				MimeMessage m = new MimeMessage(session);
				m.setFrom(new InternetAddress(from));

				m.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getUserName()));

				m.setSubject(title);
				m.setText(message);

				Transport.send(m);
			} catch (MessagingException m) {
				m.printStackTrace();
			}
		});
		System.out.println("Message verzenden is gelukt.");
	}

	private void sendEmailToOneReceiver(String title, String message, User user) {

		try {
			MimeMessage m = new MimeMessage(session);
			m.setFrom(new InternetAddress(from));

			m.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getUserName()));

			m.setSubject(title);
			m.setText(message);

			Transport.send(m);
		} catch (MessagingException m) {
			m.printStackTrace();
		}

		System.out.println("Message verzenden is gelukt.");
	}

	private void sendPasswordEmail(User user) {
		int password = passwordGenerator();
		String title = "Herstel jouw it-lab wachtwoord.";
		String message = String.format(
				"Beste %s,\n\nJouw wachtwoord werd ingesteld op %d.\nLog in en ga naar instellingen om een zelfgekozen wachtwoord in te stellen.\n\nVriendelijke groeten,\nHet IT-Lab\nHoGent\n",
				user.getFirstName(), password);
		
		new UserController().changePassword(user.getUserName(), String.valueOf(password)); //gewijzigde passwoord opslaan

		sendEmailToOneReceiver(title, message, user);
	}

	private int passwordGenerator() {
		SecureRandom random = new SecureRandom();
		int password = random.nextInt(90000) + 10000;

		return password;
	}
	
	private void sendEmailToOneReceiverWithMailAdres(String title, String message, String mailadres) {

		try {
			MimeMessage m = new MimeMessage(session);
			m.setFrom(new InternetAddress(from));

			m.addRecipient(Message.RecipientType.TO, new InternetAddress(mailadres));

			m.setSubject(title);
			m.setText(message);

			Transport.send(m);
		} catch (MessagingException m) {
			m.printStackTrace();
		}

		System.out.println("Message verzenden is gelukt.");
	}
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      throw new IllegalArgumentException("geef een geldig email adres");
		   }
		   return result;
		}
}
