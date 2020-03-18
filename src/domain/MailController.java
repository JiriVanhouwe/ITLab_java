package domain;

import java.security.SecureRandom;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
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

	public MailController(String email) { //voor passwoord te wijzigen, is een standaard e-mail
		prepareToEmail();
		sendPasswordEmail(email);
	}
	
	public MailController(String title, String message, List<String> receivers) { //als je meerdere mensen tegelijk wil sturen, hier geef je zelf de boodschap en titel op
		prepareToEmail();
		sendEmailToMultipleReceivers(title, message, receivers);
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

	private void sendEmailToMultipleReceivers(String title, String message, List<String> receivers) {
		receivers.forEach(rec -> // het bericht sturen naar alle adressen in de lijst
		{
			try {
				MimeMessage m = new MimeMessage(session);
				m.setFrom(new InternetAddress(from));

				m.addRecipient(Message.RecipientType.TO, new InternetAddress(rec));

				m.setSubject(title);
				m.setText(message);

				Transport.send(m);
			} catch (MessagingException m) {
				m.printStackTrace();
			}
		});
		System.out.println("Message verzenden is gelukt.");
	}

	private void sendEmailToOneReceiver(String title, String message, String receiver) {

		try {
			MimeMessage m = new MimeMessage(session);
			m.setFrom(new InternetAddress(from));

			m.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

			m.setSubject(title);
			m.setText(message);

			Transport.send(m);
		} catch (MessagingException m) {
			m.printStackTrace();
		}

		System.out.println("Message verzenden is gelukt.");
	}

	private void sendPasswordEmail(String email) {
		int password = passwordGenerator();
		String title = "Herstel jouw it-lab wachtwoord.";
		String message = String.format(
				"Beste,\n\nJouw wachtwoord werd ingesteld op %d.\nLog in en ga naar instellingen om een zelfgekozen wachtwoord in te stellen.\n\nVriendelijke groeten,\nHet it-lab\nHoGent\n",
				password);
		
		new UserController().changePassword(email, String.valueOf(password)); //gewijzigde passwoord opslaan

		sendEmailToOneReceiver(title, message, email);
	}

	private int passwordGenerator() {
		SecureRandom random = new SecureRandom();
		int password = random.nextInt(90000) + 10000;

		return password;
	}
}
