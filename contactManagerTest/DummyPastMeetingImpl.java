package contactManagerTest;

import java.util.Calendar;
import java.util.Set;

import contactManager.MeetingImpl;
import contactManagerInterfaces.Contact;
import contactManagerInterfaces.PastMeeting;

public class DummyPastMeetingImpl extends MeetingImpl implements PastMeeting {

	public DummyPastMeetingImpl(int id, Calendar meetingDate,
			Set<Contact> attendingContacs) {
		super(id, meetingDate, attendingContacs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

}
