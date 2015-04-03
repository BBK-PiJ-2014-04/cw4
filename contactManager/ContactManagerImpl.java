package contactManager;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import contactManager.ContactImpl;
import contactManager.FutureMeetingImpl;
import contactManager.MeetingImpl;
import contactManager.PastMeetingImpl;
import contactManagerInterfaces.Contact;
import contactManagerInterfaces.ContactManager;
import contactManagerInterfaces.FutureMeeting;
import contactManagerInterfaces.Meeting;
import contactManagerInterfaces.PastMeeting;

/**
 * @author MscDevelopment
 *
 */
/**
 * @author MscDevelopment
 *
 */
/**
 * @author MscDevelopment
 *
 */
public class ContactManagerImpl implements ContactManager {

	private List<Contact> contactsList;
	private List<Meeting> meetingsList;
	private String contactManagerXml = ""; 
	
	
	/**
	 * Constructor to initialise a new ContactManager, without restore
	 */
	public ContactManagerImpl() {
		contactsList = new ArrayList<Contact>();
		meetingsList = new ArrayList<Meeting>();
	}
	
	/**
	 * Constructor to initialise the ContactManager restoring a previous session(saved on a XML file)
	 * 
	 * @param xml
	 */
	public ContactManagerImpl(String xml) {
		contactsList = new ArrayList<Contact>();
		meetingsList = new ArrayList<Meeting>();
		contactManagerXml = xml;
		this.loadToXML(xml);
	}


	
	/**
	 * @see @inheritDoc
	 */
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

	/**
	 * @see @inheritDoc
	 */
	@Override
	public PastMeeting getPastMeeting(int id) {
		for(Iterator<Meeting> i = meetingsList.iterator(); i.hasNext(); ) {
		    Meeting item = i.next();
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
	
	/**
	 * @see @inheritDoc
	 */
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		for(Iterator<Meeting> i = meetingsList.iterator(); i.hasNext(); ) {
		    Meeting item = i.next();
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

	/**
	 * @see @inheritDoc
	 */
	@Override
	public Meeting getMeeting(int id) {
		for(Iterator<Meeting> i = meetingsList.iterator(); i.hasNext(); ) {
		    Meeting item = i.next();
		    if(id == item.getId()) {
		    	return (Meeting) item;
		    }
		}
		return null;
	}
	
	/**
	 * @see @inheritDoc
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		if(contact == null) {
			throw new IllegalArgumentException("The Contact passed can't be null");
		}
		checkRegisteredContacts(contact);
		List<Meeting> listOfMeetings = new ArrayList<Meeting>();
		for(Iterator<Meeting> i = meetingsList.iterator(); i.hasNext(); ) {
		    FutureMeetingImpl item = (FutureMeetingImpl) i.next();
		    if(item.getDate().after(new GregorianCalendar()) && item.getContacts().contains(contact)) {
		    	listOfMeetings.add(item);
		    }
		}
		Collections.sort(listOfMeetings, (precMeeting,succMeeting) -> precMeeting.getDate().compareTo(succMeeting.getDate()));
		return listOfMeetings;
	}

	//From discussion on the Forum the lecturer suggested that, despite the name, this method must return ALL the meetings, regardless when they will happen or have happened
	/**
	 * @see @inheritDoc
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		//I will consider just the YEAR/MONTH/DAY of the date passed and then give back all the meetings on that day ordered
		List<Meeting> listOfMeetings = new ArrayList<Meeting>();
		for(Iterator<Meeting> i = meetingsList.iterator(); i.hasNext(); ) {
		    MeetingImpl item = (MeetingImpl) i.next();
		    if(clearDate(item.getDate()).compareTo(clearDate(date)) == 0) {
		    	listOfMeetings.add(item);
		    }
		}
		Collections.sort(listOfMeetings, (precMeeting,succMeeting) -> precMeeting.getDate().compareTo(succMeeting.getDate()));
		return listOfMeetings;
	}

	/**
	 * @see @inheritDoc
	 */
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		if(contact == null) {
			throw new IllegalArgumentException("The Contact passed can't be null");
		}
		checkRegisteredContacts(contact);
		List<PastMeeting> listOfMeetings = new ArrayList<PastMeeting>();
		for(Iterator<Meeting> i = meetingsList.iterator(); i.hasNext(); ) {
		    PastMeetingImpl item = (PastMeetingImpl) i.next();
		    if(item.getDate().before(new GregorianCalendar()) && item.getContacts().contains(contact)) {
		    	listOfMeetings.add(item);
		    }
		}
		Collections.sort(listOfMeetings, (precMeeting,succMeeting) -> precMeeting.getDate().compareTo(succMeeting.getDate()));
		return listOfMeetings;
	}

	/**
	 * @see @inheritDoc
	 */
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		int nextID = getNextAvailableID(meetingsList);
		meetingsList.add(new PastMeetingImpl(nextID, date, contacts, text));
	}

	/**
	 * @see @inheritDoc
	 */
	@Override
	public void addMeetingNotes(int id, String text) {
		if(text == null) {
			throw new NullPointerException("The notes can't be null!");
		}
		boolean exists = false;
		for(Iterator<Meeting> i = meetingsList.iterator(); i.hasNext(); ) {
			Meeting item = i.next();
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

	/**
	 * @see @inheritDoc
	 */
	@Override
	public void addNewContact(String name, String notes) {
		ContactImpl newContact = new ContactImpl(getNextAvailableID(contactsList),name);
		newContact.addNotes(notes);
		contactsList.add(newContact);
		
	}

	/**
	 * @see @inheritDoc
	 */
	@Override
	public Set<Contact> getContacts(int... ids) {
		Set<Contact> listOfContacts = new HashSet<Contact>();
		boolean entered = false;
		for (int m : ids) {
			entered = false;
			for(Iterator<Contact> i = contactsList.iterator(); i.hasNext(); ) {
			    Contact item = i.next();
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

	/**
	 * @see @inheritDoc
	 */
	@Override
	public Set<Contact> getContacts(String name) {
		Set<Contact> listOfContacts = new HashSet<Contact>();
		if(name == null)
		{
			throw new NullPointerException();
		}
		for(Iterator<Contact> i = contactsList.iterator(); i.hasNext(); ) {
		    Contact item = i.next();
		    listOfContacts.add(item);
		}
		return listOfContacts;
	}

	/**
	 * Saves a XML containing the Meetings List and the Contacts List either on a default path or on a specified path, based on the way the Class has been initialised (Constructor)
	 */
	@Override
	public void flush() {
		String defaultPath = System.getProperty("user.dir") +"\\ContactManager\\ContactsXML\\contacts_" + UUID.randomUUID() + ".xml";
		saveToXML((contactManagerXml.equals("")) ? defaultPath : contactManagerXml);
	}
	
	/**
	 * Getting the next available not-used ID (Pretty basic, but there's no deletion so it should be enough)
	 * 
	 * @param mylist
	 * @return int guid
	 */
	public int getNextAvailableID(List<?> mylist){
		return mylist.size() + 1;
	}
	
	/**
	 * 
	 * @return Calendar Today Date
	 */
	public Calendar getTodayDate() {
		return new GregorianCalendar();
	}
	
	/**
	 * Clearing a Calendar date to check which meetings are going to happen today
	 * 
	 * @param date
	 * @return Calendar Date with just year, month and day of the month
	 */
	public Calendar clearDate(Calendar date) {
		date.clear(Calendar.MILLISECOND);
		date.clear(Calendar.SECOND); 
		date.clear(Calendar.MINUTE);
		date.clear(Calendar.HOUR_OF_DAY);
		return date;
	}
	
	/**
	 * Checking if a contact has already been inserted into the ContactList 
	 * 
	 * @param contact
	 */
	public void checkRegisteredContacts(Contact contact) {
		if(!contactsList.contains(contact)) {
			throw new IllegalArgumentException("The contact is not registered in the Contact Manager");
	
		}
	}
	
	/**
	 * Checking if a a set of contacts has already been inserted into the ContactList 
	 * 
	 * @param contactsSet
	 */
	public void checkRegisteredContacts(Set<Contact> contactsSet) {
		if(!contactsList.containsAll(contactsSet)) {
			throw new IllegalArgumentException("One or more Contacs are not registered in the Contact Manager");
	
		}
	}
	
	/**
	 * Restoring the previously changed Contacts and Meetings
	 * Code written with the support of the StackOverlow website
	 * 
	 * @param xml
	 */
	public void loadToXML(String xml) {
		 try {
				File fXmlFile = new File(xml);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
			 
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("Contact");

				ContactImpl tempContact;
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 
						Element eElement = (Element) nNode;
						String tempName = eElement.getElementsByTagName("Name").item(0).getTextContent();
						int tempID = Integer.parseInt(eElement.getElementsByTagName("ID").item(0).getTextContent());
						tempContact = new ContactImpl(tempID,tempName);
						tempContact.addNotes(eElement.getElementsByTagName("Notes").item(0).getTextContent());
						contactsList.add(tempContact);
					}
				}
				
				NodeList mList = doc.getElementsByTagName("Meeting");

				Meeting tempMeeting;
				for (int temp = 0; temp < mList.getLength(); temp++) {
					Node nNode = mList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 
						Element eElement = (Element) nNode;
						String[] date =  eElement.getElementsByTagName("meetingDate").item(0).getTextContent().split("/");
						Calendar tempDate = new GregorianCalendar(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]),Integer.parseInt(date[3]),Integer.parseInt(date[4]));
						int tempID = Integer.parseInt(eElement.getElementsByTagName("ID").item(0).getTextContent());
						NodeList iList = doc.getElementsByTagName("MeetingContacts");
						Set<Contact> myset = new HashSet<Contact>();
						for (int insideTemp = 0; insideTemp < iList.getLength(); insideTemp++) {
							Node iNode = iList.item(temp);
							if (iNode.getNodeType() == Node.ELEMENT_NODE) {
								Element iElement = (Element) nNode;
								myset.add((Contact) this.getContacts(Integer.parseInt(iElement.getElementsByTagName("ContactID").item(0).getTextContent())).toArray()[0]);
							}
						}
						tempMeeting = new MeetingImpl(tempID, tempDate, myset);
						String tempNotes = "";
						if(tempDate.before(new GregorianCalendar())) {
							tempNotes = eElement.getElementsByTagName("Notes").item(0).getTextContent();
						}
						if(tempNotes != "") {
							Meeting tempPastMeeting = new PastMeetingImpl(tempID, tempDate, myset, tempNotes);
							meetingsList.add(tempPastMeeting);
						}				
						else {
							meetingsList.add(tempMeeting);
						}
					}
				}
				
			    } 
		 		catch (Exception e) {
			    	e.printStackTrace();
			    }
	}
	
	/**
	 * Saving the Contacts and the Meeting on a XML file
	 * Code written with the support of StackOverlow website
	 * 
	 * @param xml
	 */
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
	        
	        for(Iterator<contactManagerInterfaces.Contact> i = contactsList.iterator(); i.hasNext(); ) {
	        	Contact = dom.createElement("Contact");
	        	
	        	contactManagerInterfaces.Contact item = i.next();
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
	        for(Iterator<contactManagerInterfaces.Meeting> i = meetingsList.iterator(); i.hasNext(); ) {
	        	Meeting = dom.createElement("Meeting");
	        	contactManagerInterfaces.Meeting item = i.next();
	        	
	        	e = dom.createElement("ID");
		        e.appendChild(dom.createTextNode(((Integer)item.getId()).toString()));
		        Meeting.appendChild(e);

		        e = dom.createElement("meetingDate");
		        //to Change!!!
		        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd/hh/mm");
		        String date1 = format1.format(item.getDate().getTime());
		        e.appendChild(dom.createTextNode(date1));
		        Meeting.appendChild(e);
		        
		        e = dom.createElement("MeetingContacts");
		        
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

