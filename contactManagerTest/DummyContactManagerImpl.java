package contactManagerTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
		saveToXML(System.getProperty("user.dir") +"\\ContactManager\\ContactsXML\\contacts.xml");
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
	
	public void saveToXML(String xml) {
	    Document dom;
	    Element e = null;
	    
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        dom = db.newDocument();

	        Element rootEle = dom.createElement("ContactManager");
	        
	        Element ContactsRootEle = dom.createElement("Contacts");
	        rootEle.appendChild(ContactsRootEle);
	        Element MeetingsRootEle = dom.createElement("Meetings");
	        rootEle.appendChild(MeetingsRootEle);
	        Element Contact = null;
	        Element Meeting = null;
	        
	        for(Iterator<ContactImpl> i = contactsList.iterator(); i.hasNext(); ) {
	        	Contact = dom.createElement("Contact");
	        	
	        	ContactImpl item = i.next();
	        	e = dom.createElement("ID");
		        e.appendChild(dom.createTextNode(((Integer)item.getId()).toString()));
		        Contact.appendChild(e);

		        e = dom.createElement("Name");
		        e.appendChild(dom.createTextNode(item.getName()));
		        Contact.appendChild(e);

		        e = dom.createElement("Notes");
		        e.appendChild(dom.createTextNode(item.getNotes()));
		        Contact.appendChild(e);
		        
		        ContactsRootEle.appendChild(Contact);
			}
	        
	        Element singleContact;
	        for(Iterator<MeetingImpl> i = meetingsList.iterator(); i.hasNext(); ) {
	        	Meeting = dom.createElement("Meeting");
	        	MeetingImpl item = i.next();
	        	
	        	e = dom.createElement("ID");
		        e.appendChild(dom.createTextNode(((Integer)item.getId()).toString()));
		        Meeting.appendChild(e);

		        e = dom.createElement("meetingDate");
		        e.appendChild(dom.createTextNode(item.getDate().toString()));
		        Meeting.appendChild(e);
		        
		        e = dom.createElement("Contacts");
		        
		        for(Iterator<Contact> m = item.getContacts().iterator(); m.hasNext(); ) {
		        	ContactImpl myContact = (ContactImpl) m.next();
		        	singleContact  = dom.createElement("ContactID");
		        	singleContact.appendChild(dom.createTextNode(((Integer)myContact.getId()).toString()));
		        	e.appendChild(singleContact);
		        }
		        Meeting.appendChild(e);
		        
		        if(item.getDate().before(getTodayDate())){
		        	e = dom.createElement("Notes");
		        	e.appendChild(dom.createTextNode(((PastMeetingImpl)item).getNotes()));
		        	Meeting.appendChild(e);
		        }
		        MeetingsRootEle.appendChild(Meeting);
			}
	        
	        dom.appendChild(rootEle);
	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	            // send DOM to file
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new FileOutputStream(xml)));

	        } catch (TransformerException te) {
	            System.out.println(te.getMessage());
	        } catch (IOException ioe) {
	            System.out.println(ioe.getMessage());
	        }
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	    }
	}
}
