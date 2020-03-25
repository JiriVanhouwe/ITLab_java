package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import exceptions.InformationRequiredException;

public class SessionBuilder {

	protected Session session;
	protected HashSet<RequiredElement> requiredElements;
	
	public Session getSession() throws InformationRequiredException {
		requiredElements = new HashSet<>();
		
		if(session.getTitle() == null || session.getTitle().startsWith("New Entry") || session.getTitle().isBlank())
			requiredElements.add(RequiredElement.TITLEREQUIRED);
		
		if(session.getStartHour() == null || session.getDate() == null || session.getDate().isBefore(LocalDate.now()))
			requiredElements.add(RequiredElement.STARTDATEREQUIRED);
		
		if(session.getDate() == null || session.getEndHour() == null)
			requiredElements.add(RequiredElement.ENDDATEREQUIRED);
		
		if(session.getClassroom() == null)
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
			session.changeTitle(title);
	}
	
	
	public void buildClassroomAndMaxAtendeees(Classroom classroom ,int maxAttendee) {
		if(classroom != null) {
			session.changeClassroom(classroom);
		
		if(maxAttendee > 0 && maxAttendee <= session.getClassroom().getMaxSeats()) 
			session.changeMaxAttendee(maxAttendee);
		else 
			session.changeMaxAttendee(0);
		}
	}
	
	public void buildDates(LocalDateTime startDate, LocalDateTime endDate) {
		
		if(startDate != null && endDate !=null && startDate.isBefore(endDate) && startDate.isAfter(LocalDateTime.now().plusDays(1))) {
			if(startDate.toLocalDate().equals(endDate.toLocalDate())) {
				if(startDate.toLocalTime().isBefore(endDate.toLocalTime().minusMinutes(29).minusSeconds(59))) {
					session.changeDate(startDate.toLocalDate());
					session.changeStartHour(startDate.toLocalTime());
					session.changeEndHour(endDate.toLocalTime());
				}
			}
		}
		
	
	}
	
	public void buildGuestSpeaker(String guestSpeaker) {
		session.changeNameGuest(guestSpeaker);
	}
	
	public void buildDescription(String discription) {
		session.changeDescription(discription);
	}
	
	public void buildMedia(List<Integer> medialist) {
		session.changeMedia(medialist);
	}
	
	public void buildVideoURL(String videoURL) {
		session.changeVideoURL(videoURL);
	}
	
	public void buildHost(User host) {
		session.changeHost(host);
	}
	
	public void buildState(State state) {
		session.setStateEnum(state);
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	
}
