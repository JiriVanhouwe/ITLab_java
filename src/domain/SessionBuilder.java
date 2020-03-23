package domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import exceptions.InformationRequiredException;

public class SessionBuilder {

	protected Session session;
	protected HashSet<RequiredElement> requiredElements;
	
	public Session getSession() throws InformationRequiredException {
		requiredElements = new HashSet<>();
		
		if(session.getTitle() == null || session.getTitle().startsWith("New Entry"))
			requiredElements.add(RequiredElement.TITLEREQUIRED);
		
		
		if(session.getStartHour() == null || session.getDate() == null )
			requiredElements.add(RequiredElement.STARTDATEREQUIRED);
		
		if(session.getDate() == null || session.getEndHour() == null)
			requiredElements.add(RequiredElement.ENDDATEREQUIRED);
		
		if(session.getClass() == null)
			requiredElements.add(RequiredElement.CLASSROOMREQUIRED);
		
		if(session.getMaxAttendee() == 0)
			requiredElements.add(RequiredElement.ATENDEESREQUIRED);

		if(!requiredElements.isEmpty())
			throw new InformationRequiredException(requiredElements);
		
			
		return this.session;
		
	}
	
	public void createSession() {
		session = new Session();
	}
	
	public void buildTitle(String title) {
		if(!title.isBlank())
			session.setTitle(title);
	}
	
	
	public void buildClassroomAndMaxAtendeees(Classroom classroom ,int maxAttendee) {
		if(classroom != null)
			session.setClassroom(classroom);
		
		if(maxAttendee > 0 && maxAttendee <= session.getClassroom().getMaxSeats()) 
			session.setMaxAttendee(maxAttendee);
		else 
			session.setMaxAttendee(0);
	}
	
	public void buildDates(LocalDateTime startDate, LocalDateTime endDate) {
		
		if(startDate != null && endDate !=null && startDate.isBefore(endDate) && startDate.isAfter(LocalDateTime.now().plusDays(1))) {
			if(startDate.toLocalDate().equals(endDate.toLocalDate())) {
				if(startDate.toLocalTime().isBefore(endDate.toLocalTime().minusMinutes(29).minusSeconds(59))) {
					session.setDate(startDate.toLocalDate());
					session.setStartHour(startDate.toLocalTime());
					session.setEndHour(endDate.toLocalTime());
				}
			}
		}
		
	
	}
	
	public void buildGuestSpeaker(String guestSpeaker) {
		session.setNameGuest(guestSpeaker);
	}
	
	public void buildDescription(String discription) {
		session.setDescription(discription);
	}
	
	public void buildMedia(List<Integer> medialist) {
		session.setMedia(medialist);
	}
	
	public void buildVideoURL(String videoURL) {
		session.setVideoURL(videoURL);
	}
	
	public void buildHost(User host) {
		session.setHost(host);
	}
	
	public void buildState(State state) {
		session.setStateEnum(state);
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	
}
