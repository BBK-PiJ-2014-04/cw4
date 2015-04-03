package contactManager;
/**
 * @see FutureMeeting
 * 
 * 
 * @author SalvatoreCardali
 *
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import contactManagerInterfaces.Contact;
import contactManagerInterfaces.FutureMeeting;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {

	/**
	 * The Constructor gets and populates all the properties. 
	 * The id is passed but it's generated/checked by the ContactManager Class
	 * A class can be instantiated as or cast to FutureMeeting only if it has a date after the current date
	 * 
	 * @param id
	 * @param meetingDate
	 * @param attendingContacs
	 */
	public FutureMeetingImpl(int id, Calendar meetingDate,
			Set<Contact> attendingContacs) {
		super(id, meetingDate, attendingContacs);
		if(meetingDate.after(new GregorianCalendar()))
		{
			throw new IllegalArgumentException("A FutureMeeting must be in the future");
		}
	}

}
