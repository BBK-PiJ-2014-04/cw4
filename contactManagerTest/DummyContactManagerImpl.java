package contactManagerTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import contactManager.ContactImpl;
import contactManager.FutureMeetingImpl;
import contactManager.MeetingImpl;
import contactManager.PastMeetingImpl;
import contactManagerInterfaces.Contact;
import contactManagerInterfaces.ContactManager;
import contactManagerInterfaces.FutureMeeting;
import contactManagerInterfaces.Meeting;
import contactManagerInterfaces.PastMeeting;

public class DummyContactManagerImpl implements ContactManager {

	List<ContactImpl> contactsList = new ArrayList<ContactImpl>();
	List<MeetingImpl> meetingsList = new ArrayList<MeetingImpl>();
	
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if(contacts == null) {
			throw new IllegalArgumentException("The Contacts set can't be null");
		}
		int nextID;
		checkRegisteredContacts(contacts);
		nextID = getNextAvailableID(meetingsList);
		meetingsList.add(new FutureMeetingImpl(nextID, date, contacts));
		return nextID;
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		for(Iterator<MeetingImpl> i = meetingsList.iterator(); i.hasNext(); ) {
		    MeetingImpl item = i.next();
		    if(id == item.getId()) {
		    	if(item.getDate().after(getTodayDate())) {
		    		throw new IllegalArgumentException("The requested meeting is in the future!");
		    	}
		    	else {
		    		return (PastMeeting) item;
		    	}
		    }
		}
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		for(Iterator<MeetingImpl> i = meetingsList.iterator(); i.hasNext(); ) {
		    MeetingImpl item = i.next();
		    if(id == item.getId()) {
		    	if(item.getDate().before(getTodayDate())) {
		    		throw new IllegalArgumentException("The requested meeting is in the future!");
		    	}
		    	else {
		    		return (FutureMeeting) item;
		    	}
		    }
		}
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		for(Iterator<MeetingImpl> i = meetingsList.iterator(); i.hasNext(); ) {
		    MeetingImpl item = i.next();
		    if(id == item.getId()) {
		    	return (Meeting) item;
		    }
		}
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		if(contact == null) {
			throw new IllegalArgumentException("The Contact passed can't be null");
		}
		checkRegisteredContacts(contact);
		List<Meeting> listOfMeetings = new ArrayList<Meeting>();
		for(Iterator<MeetingImpl> i = meetingsList.iterator(); i.hasNext(); ) {
		    FutureMeetingImpl item = (FutureMeetingImpl) i.next();
		    if(item.getDate().after(new GregorianCalendar()) && item.getContacts().contains(contact)) {
		    	listOfMeetings.add(item);
		    }
		}
		Collections.sort(listOfMeetings, (precMeeting,succMeeting) -> precMeeting.getDate().compareTo(succMeeting.getDate()));
		return listOfMeetings;
	}

	//From discussion on the Forum the lecturer suggested that, despite the name, this method must return ALL the meetings, regardless when they will happen or have happened
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		//I will consider just the YEAR/MONTH/DAY of the date passed and then give back all the meetings on that day ordered
		List<Meeting> listOfMeetings = new ArrayList<Meeting>();
		for(Iterator<MeetingImpl> i = meetingsList.iterator(); i.hasNext(); ) {
		    MeetingImpl item = (MeetingImpl) i.next();
		    if(clearDate(item.getDate()).compareTo(clearDate(date)) == 0) {
		    	listOfMeetings.add(item);
		    }
		}
		Collections.sort(listOfMeetings, (precMeeting,succMeeting) -> precMeeting.getDate().compareTo(succMeeting.getDate()));
		return listOfMeetings;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		if(contact == null) {
			throw new IllegalArgumentException("The Contact passed can't be null");
		}
		checkRegisteredContacts(contact);
		List<PastMeeting> listOfMeetings = new ArrayList<PastMeeting>();
		for(Iterator<MeetingImpl> i = meetingsList.iterator(); i.hasNext(); ) {
		    PastMeetingImpl item = (PastMeetingImpl) i.next();
		    if(item.getDate().before(new GregorianCalendar()) && item.getContacts().contains(contact)) {
		    	listOfMeetings.add(item);
		    }
		}
		Collections.sort(listOfMeetings, (precMeeting,succMeeting) -> precMeeting.getDate().compareTo(succMeeting.getDate()));
		return listOfMeetings;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		int nextID = getNextAvailableID(meetingsList);
		meetingsList.add(new PastMeetingImpl(nextID, date, contacts, text));
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		if(text == null) {
			throw new NullPointerException("The notes can't be null!");
		}
		boolean exists = false;
		for(Iterator<MeetingImpl> i = meetingsList.iterator(); i.hasNext(); ) {
			MeetingImpl item = i.next();
		    if(id == item.getId()) {
		    	if(item.getDate().after(getTodayDate())) {
		    		throw new IllegalStateException("You can't modify notes of a future Meeting");
		    	}
		    	exists = true;
		    	((PastMeetingImpl) item).setNotes(text);
		    	break;
		    }
		}
		if(!exists) {
			throw new IllegalArgumentException("The Meeting ID passed doesn't exist");
		}
		
	}

	@Override
	public void addNewContact(String name, String notes) {
		ContactImpl newContact = new ContactImpl(getNextAvailableID(contactsList),name);
		newContact.addNotes(notes);
		contactsList.add(newContact);
		
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		Set<Contact> listOfContacts = new HashSet<Contact>();
		boolean entered = false;
		for (int m : ids) {
			entered = false;
			for(Iterator<ContactImpl> i = contactsList.iterator(); i.hasNext(); ) {
			    ContactImpl item = i.next();
			    if(m == item.getId()) {
			    	listOfContacts.add(item);
			    	entered = true;
			    }
			}
			if(!entered) {
		    	break;
		    }
		}
		if(contactsList.size() == 0 || !entered) {
			throw new IllegalArgumentException("One of the IDs doesn't belong to the contact list");
		}
		return listOfContacts;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		Set<Contact> listOfContacts = new HashSet<Contact>();
		if(name == null)
		{
			throw new NullPointerException();
		}
		for(Iterator<ContactImpl> i = contactsList.iterator(); i.hasNext(); ) {
		    ContactImpl item = i.next();
		    listOfContacts.add(item);
		}
		return listOfContacts;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
	
	public int getNextAvailableID(List<?> mylist){
		return mylist.size() + 1;
	}
	
	public void checkRegisteredContacts(Set<Contact> contactsSet) {
		if(!contactsList.containsAll(contactsSet)) {
			throw new IllegalArgumentException("One or more Contacs are not registered in the Contact Manager");
	
		}
	}
	
	public Calendar getTodayDate() {
		return new GregorianCalendar();
	}
	
	public Calendar clearDate(Calendar date) {
		date.clear(Calendar.MILLISECOND);
		date.clear(Calendar.SECOND); 
		date.clear(Calendar.MINUTE);
		date.clear(Calendar.HOUR_OF_DAY);
		return date;
	}
	
	public void checkRegisteredContacts(Contact contact) {
		if(!contactsList.contains(contact)) {
			throw new IllegalArgumentException("The contact is not registered in the Contact Manager");
	
		}
	}
}
