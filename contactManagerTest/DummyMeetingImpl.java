package contactManagerTest;
import java.util.Calendar;
import java.util.Set;

import contactManagerInterfaces.Contact;
import contactManagerInterfaces.Meeting;

public class DummyMeetingImpl implements Meeting {

	public int id;

	public DummyMeetingImpl(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public Calendar getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contact> getContacts() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
