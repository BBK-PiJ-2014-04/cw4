package contactManagerTest;
import java.util.Calendar;
import java.util.Set;

import contactManagerInterfaces.Contact;
import contactManagerInterfaces.Meeting;

public class DummyMeetingImpl implements Meeting {

	private int id;
	private Calendar meetingDate;
	

	public DummyMeetingImpl(int id, Calendar meetingDate) {
		this.id = id;
		this.meetingDate = meetingDate;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public Calendar getDate() {
		return meetingDate;
	}

	@Override
	public Set<Contact> getContacts() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
