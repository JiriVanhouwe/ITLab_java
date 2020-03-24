package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.StyledEditorKit.ItalicAction;

import exceptions.InformationRequiredException;
import javafx.scene.image.Image;

public class SessionController extends Controller {
	
	private final SessionBuilder sb;

	public SessionController() {
		super();
		sb = new SessionBuilder();
	}

	public GuiSession giveSession(String sessionID) {
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
	
	public void removeSession(String sessionID) {
		Session session = (Session) giveSession(sessionID);
		
		itLab.getEntityManager().getTransaction().begin();
		itLab.getEntityManager().remove(session);
		itLab.getEntityManager().getTransaction().commit();
		
		itLab.removeSession(session);
	}

	public String changeSession(String sessionID, String title, Classroom classroom, LocalDateTime startDate, LocalDateTime endDate, int maxAttendee, String description, String nameGuest, List<Integer> media, String videoURL, State state) throws InformationRequiredException {
		
		if (sessionID.endsWith("#")) {
			int id = Integer.parseInt(sessionID.substring(0, sessionID.length() - 1));
			Session session = itLab.getCurrentSessioncalendar().giveSession(id);
			if(itLab.getLoggedInUser().equals(session.getHost()) || itLab.getLoggedInUser().getUserType().equals(UserType.HEAD))
			itLab.getEntityManager().getTransaction().begin();
			sb.setSession(session);
			sb.buildTitle(title);
			sb.buildClassroomAndMaxAtendeees(classroom, maxAttendee);
			sb.buildDates(startDate, endDate);
			sb.buildDescription(description);
			sb.buildMedia(media);
			sb.buildVideoURL(videoURL);
			sb.buildState(state);
			sb.buildGuestSpeaker(nameGuest);
			sb.buildHost(itLab.getLoggedInUser());
			itLab.getCurrentSessioncalendar().addSession(session);
			itLab.getEntityManager().persist(session);
			itLab.getEntityManager().getTransaction().commit();
			return sessionID;
		} else {
			return this.createSession(title, startDate, endDate, classroom, maxAttendee, description, nameGuest, media, videoURL, state);
		}
	}
	
	public List<GuiSession> giveSessionsBetweenDates(LocalDate startDate, LocalDate endDate) {
		return itLab.giveSessionsBetweenDates(startDate, endDate)
					.stream()
					.map(session -> (GuiSession)session)
					.collect(Collectors.toList());
	}

	public String createSession(String title, LocalDateTime startDate, LocalDateTime endDate, Classroom classroom,
			int maxAttendee, String description, String nameGuest, List<Integer> media, String videoURL, State state) throws InformationRequiredException {
		sb.createSession();
		sb.buildTitle(title);
		sb.buildDates(startDate, endDate);
		sb.buildClassroomAndMaxAtendeees(classroom , maxAttendee);
		sb.buildDescription(description);
		sb.buildGuestSpeaker(nameGuest);
		sb.buildMedia(media);
		sb.buildVideoURL(videoURL);
		sb.buildHost(itLab.getLoggedInUser());
		sb.buildState(state);
		
		
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
			byte[] imageBytes = (byte[]) itLab.getEntityManager().createNativeQuery("SELECT image FROM IMAGE WHERE IMAGEKEY = '" + id + "' AND IMAGE IS NOT NULL").getSingleResult();
			fo.write(imageBytes);
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			return null;
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



	public List<GuiSession> giveSessionsClosedAndFinshedCurrentCalendar() {
		List<GuiSession> list = null ;
		if(!itLab.getLoggedInUser().getUserType().equals(UserType.HEAD)) {
			list = itLab.giveSessions().stream().filter(e -> itLab.getLoggedInUser().equals(e.getHost())).collect(Collectors.toList());
		} else {
			list = giveSessionsCurrentCalendar();
		}
		return list.stream().filter(e -> e.getStateEnum().equals(State.CLOSED) || e.getStateEnum().equals(State.FINISHED) ).collect(Collectors.toList());
	}

	public void removeFeedbackFromSession(GuiFeedback selectedFeedback, GuiSession selectedSession) {
		itLab.getEntityManager().getTransaction().begin();
		((Session) selectedSession).removeFeedback((Feedback)selectedFeedback);
		itLab.getEntityManager().getTransaction().commit();
		
	}


}
