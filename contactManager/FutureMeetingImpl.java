package contactManager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import contactManagerInterfaces.Contact;
import contactManagerInterfaces.FutureMeeting;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {

	public FutureMeetingImpl(int id, Calendar meetingDate,
			Set<Contact> attendingContacs) {
		super(id, meetingDate, attendingContacs);
		if(meetingDate.compareTo(new GregorianCalendar()) < 0)
		{
			throw new IllegalArgumentException("A FutureMeeting must be in the future");
		}
	}

}
