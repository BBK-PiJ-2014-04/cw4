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
		if(attendingContacs.size() > 0) {
			this.attendingContacs = attendingContacs;
		}
		else {
			throw new IllegalArgumentException("The name of the Contact can't be null");
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
