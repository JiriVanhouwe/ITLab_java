package domain;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import exceptions.InformationRequiredException;
import javafx.scene.image.Image;

public class SessionController extends Controller {
	
	private final SessionBuilder sb;

	public SessionController() {
		super();
		sb = new SessionBuilder();
	}

	public Session giveSession(String sessionID) {
		for (Session s : itLab.giveSessions()) {
			int id;
			if (sessionID.endsWith("#")) {
				id = Integer.parseInt(sessionID.substring(0, sessionID.length() - 1));
			} else {
				id = Integer.parseInt(sessionID);
			}
			
			if(id == s.getSessionID()) {
				return s;
			}
		}
		return null;
	}

	public List<GuiSession> giveSessionsCurrentCalendar() {
		return this.itLab.giveSessions()
				.stream()
				.map(session -> (GuiSession)session)
				.collect(Collectors.toList());
	}

	public String changeSession(String sessionID, String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String description, String nameGuest, List<Integer> media) throws InformationRequiredException {
		if (sessionID.endsWith("#")) {
			int id = Integer.parseInt(sessionID.substring(0, sessionID.length() - 1));
			this.itLab.changeSession(id, title, classroom, startDate, endDate, maxAttendee, description, nameGuest, media);
			return sessionID;
		} else {
			return this.createSession(title, startDate, endDate, classroom, maxAttendee, description, nameGuest, media);
		}

	}

	// waarom hier id terug geven -> het entryid moet onmiddelijk aangepast orden
	// naar het juiste sessionid, want een entry neemt anders zijn eigen nummering
	// aan die niet gelijk zal lopen met sessions
	public String createSession(String title, LocalDateTime startDate, LocalDateTime endDate, Classroom classroom,
			int maxAttendee, String description, String nameGuest, List<Integer> media) throws InformationRequiredException {
		sb.createSession();
		sb.buildTitle(title);
		sb.buildDates(startDate, endDate);
		sb.buildClassroomAndMaxAtendeees(classroom , maxAttendee);
		sb.buildDescription(description);
		sb.buildGuestSpeaker(nameGuest);
		sb.buildMedia(media);
		
		
		Session session = sb.getSession();
		
		itLab.addSession(session);
		return String.format("%d#", session.getSessionID());
	}

	public List<Classroom> giveAllClassrooms() {

		return itLab.getEntityManager().createNamedQuery("Classroom.findAll", Classroom.class).getResultList();

	}

	public Classroom giveClassroom(String text) {
		return itLab.getEntityManager().createNamedQuery("Classroom.findById", Classroom.class)
				.setParameter("classid", text).getSingleResult();
	}
	
	public Image giveImage(int id) {
		//We have to get the image from the db and return it
		try {
			//Specify where the file has to be temporarily saved, inside the project folder
			FileOutputStream  fo = new FileOutputStream("images\\TempImage.png");
			//Execute the query and read the result to the outputstream
			fo.write( (byte[]) itLab.getEntityManager().createNativeQuery("SELECT image FROM IMAGE WHERE IMAGEKEY = '" + id + "'").getSingleResult());
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Image("file:images\\TempImage.png");
	}
	
	public Integer saveImage(Image image) {
		
		//Save the image to the Image table
		itLab.getEntityManager().getTransaction().begin();
		FileInputStream in;
		try {
			//Transform URL to URI
			in = new FileInputStream(image.getUrl().replaceAll("file:", ""));
			itLab.getEntityManager().createNativeQuery("INSERT INTO Image VALUES(?)")
			.setParameter(1, in.readAllBytes())
			.executeUpdate();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		itLab.getEntityManager().getTransaction().commit();
		
		//Return the id that has been generated
		return ((BigDecimal) itLab.getEntityManager().createNativeQuery("SELECT IDENT_CURRENT('Image')").getSingleResult()).intValue();
	}
}
