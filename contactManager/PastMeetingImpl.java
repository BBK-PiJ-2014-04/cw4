package contactManager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import contactManager.MeetingImpl;
import contactManagerInterfaces.Contact;
import contactManagerInterfaces.PastMeeting;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	String notes;
	
	/**
	 * The Constructor gets and populates all the properties. 
	 * The id is passed but it's generated/checked by the ContactManager Class
	 * A class can be instantiated as or cast to PastMeetingImpl only if it has a date before the current date
	 * A null param notes, will throw a NullPointer Exception
	 * 
	 * @param id
	 * @param meetingDate
	 * @param attendingContacs
	 * @param notes
	 */
	public PastMeetingImpl(int id, Calendar meetingDate,
			Set<Contact> attendingContacs, String notes) {
		super(id, meetingDate, attendingContacs);
		if(meetingDate.before(new GregorianCalendar()))
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
	
	/**
	 * @see @inheritDoc
	 */
	@Override
	public String getNotes() {
		return (notes != "") ? notes : "There are no notes for this meeting";
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
