package contactManagerTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import contactManager.ContactImpl;
import contactManager.FutureMeetingImpl;
import contactManagerInterfaces.Contact;
import contactManagerInterfaces.ContactManager;
import contactManagerInterfaces.FutureMeeting;
import contactManagerInterfaces.Meeting;
import contactManagerInterfaces.PastMeeting;

public class DummyContactManagerImpl implements ContactManager {

	List<ContactImpl> contactsList = new ArrayList<ContactImpl>();
	
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if(contactsList.containsAll(contacts)) {
			Meeting futureMeeting = new FutureMeetingImpl(1, date, contacts);
		}
		else {
			throw new IllegalArgumentException("One or more Contacs are not registered in the Contact Manager");
		}
		return 0;
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
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

}
