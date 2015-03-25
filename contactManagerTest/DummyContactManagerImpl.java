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
		    	if(item.getDate().compareTo(getTodayDate()) > 0) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
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
		    if(item.getDate().compareTo(new GregorianCalendar()) < 0 && item.getContacts().contains(contact)) {
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
		// TODO Auto-generated method stub
		
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
	
	public void checkRegisteredContacts(Contact contact) {
		if(!contactsList.contains(contact)) {
			throw new IllegalArgumentException("The contact is not registered in the Contact Manager");
	
		}
	}
}
