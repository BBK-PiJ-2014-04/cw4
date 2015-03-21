package contactManager;

import java.util.Calendar;
import java.util.Set;

import contactManagerInterfaces.Contact;
import contactManagerInterfaces.Meeting;

public class MeetingImpl implements Meeting {
	
	private int id;
	private Calendar meetingDate;
	private Set<Contact> attendingContacs;
	

	public MeetingImpl(int id, Calendar meetingDate, Set<Contact> attendingContacs) {
		this.id = id;
		this.meetingDate = meetingDate;
		this.attendingContacs = attendingContacs;
		try {
			if(this.attendingContacs.size() < 1) {
				throw new IllegalArgumentException("The number of attending Contacts must be at least 1");
			}
		}
		catch(NullPointerException ex) {
			throw ex;
		}
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
		return attendingContacs;
	}


}
