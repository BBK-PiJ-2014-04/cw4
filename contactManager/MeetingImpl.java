package contactManager;

import java.util.Calendar;
import java.util.Set;

import contactManagerInterfaces.Meeting;

public class MeetingImpl {
	
	public int id;
	private Calendar meetingDate;

	public MeetingImpl(int id, Calendar meetingDate) {
		this.id = id;
		this.meetingDate = meetingDate;
	}
	
	public int getId() {
		return id;
	}
	
	public Calendar getDate() {
		return meetingDate;
	}


}
