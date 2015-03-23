package contactManager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import contactManager.MeetingImpl;
import contactManagerInterfaces.Contact;
import contactManagerInterfaces.PastMeeting;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	String notes;
	
	public PastMeetingImpl(int id, Calendar meetingDate,
			Set<Contact> attendingContacs, String notes) {
		super(id, meetingDate, attendingContacs);
		if(meetingDate.compareTo(new GregorianCalendar()) >= 0)
		{
			throw new IllegalArgumentException("A PastMeeting must be in the past");
		}
		if(notes == null) {
			throw new NullPointerException("The notes can't be null");
		}
		else {
			this.notes = notes;
		}
	}

	@Override
	public String getNotes() {
		return (notes != "") ? notes : "There are no notes for this meeting";
	}

}
