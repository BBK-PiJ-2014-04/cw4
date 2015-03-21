package contactManagerTest;

import java.util.Calendar;
import java.util.Set;

import contactManager.MeetingImpl;
import contactManagerInterfaces.Contact;
import contactManagerInterfaces.FutureMeeting;

public class DummyFutureMeetingImpl extends MeetingImpl implements FutureMeeting {

	public DummyFutureMeetingImpl(int id, Calendar meetingDate,
			Set<Contact> attendingContacs) {
		super(id, meetingDate, attendingContacs);
		// TODO Auto-generated constructor stub
	}

}
