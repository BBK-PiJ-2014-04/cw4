package contactManagerTest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import contactManager.ContactImpl;
import contactManagerInterfaces.*;

public class ContactManagerTest {
	
	Contact testContact;
	Contact testContact2;
	Contact testContact3;
	ContactManager testCM;
	Calendar pastDate;
	Calendar futureDate;
	private Set<Contact> testContacts = new HashSet<Contact>();
	
	
	@Before
	public void instantiateClass() {
		pastDate = new GregorianCalendar(1986,07,27);
		futureDate = new GregorianCalendar(2015,07,27);
		testCM = new DummyContactManagerImpl();
		testContact = new ContactImpl(1,"FirstContact");
		testContact2 = new ContactImpl(2,"FirstContact2");
	}
	
	//The following tests will require very little implementation, as they've been already handled in the implementation of the Meeting Class
	@Test(expected = NullPointerException.class)
	public final void addFutureMeetingShouldNotAcceptANullContactList() {
		testCM.addFutureMeeting(null, futureDate);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addFutureMeetingShouldNotAcceptAPastDate() {
		testContacts.add(testContact);
		testContacts.add(testContact2);
		testCM.addFutureMeeting(testContacts, pastDate);
	}
	
	//We need to test now if the Contacts passed to addFutureMeeting have already been added to the Contact Manager.
	//So, we will now develop tests to add Contacts, hence we're going to need a structure to save contacts in.
	
	@Test(expected = IllegalArgumentException.class)
	public final void addFutureMeetingShouldNotAcceptContactsThatAreNotStored() {
		testContacts.add(testContact);
		testContacts.add(testContact2);
		testCM.addFutureMeeting(testContacts, futureDate);
	}
	
	@Test
	public final void addFutureMeetingShouldReturnValidAndDifferentIds() {
		String name = "something";
		String notes = "something";
		Calendar futureDate2 = new GregorianCalendar(2015,07,29);
		testCM.addNewContact(name, notes);
		int firstID = testCM.addFutureMeeting(testCM.getContacts(name), futureDate);
		int secondID = testCM.addFutureMeeting(testCM.getContacts(name), futureDate2);
		Assert.assertNotEquals(firstID, secondID);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getContactsShouldNotAcceptNull() {
		String name = null;
		testCM.getContacts(name);
	}
	
	@Test
	public final void getContactsShouldNotReturnNull() {
		String name = "test";
		Assert.assertNotNull(testCM.getContacts(name));
	}
	
	@Test
	public final void getContactsShouldReturnASet() {
		String name = "test";
		Assert.assertTrue(testCM.getContacts(name) instanceof Set<?>);
	}
	
	//Again, mostly of the functionality for addNewContact have already been tested for the Contact Class
	//So, the tests, will succeed with a minimum implementation.
	@Test(expected = IllegalArgumentException.class)
	public final void addNewContactShouldNotAcceptNullForName() {
		String name = null;
		String notes = "something";
		testCM.addNewContact(name, notes);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addNewContactShouldNotAcceptNullForNotes() {
		String name = "something";
		String notes = null;
		testCM.addNewContact(name, notes);
	}
	
	@Test
	public final void addNewContactShouldAddTheContactOnTheContacsList() {
		String name = "something";
		String notes = "something";
		boolean alreadyIn = false;
		boolean nowIn = false;
		//Making sure the name is not already in
		do {
			for(Iterator<Contact> i = testCM.getContacts(name).iterator(); i.hasNext(); ) {
				Contact item = (Contact) i.next();
			    if(item.getName().equals(name))
			    	alreadyIn = true;
			}
			if(alreadyIn){
				name += "1";
			}
		} while(alreadyIn);
		testCM.addNewContact(name, notes);
		for(Iterator<Contact> i = testCM.getContacts(name).iterator(); i.hasNext(); ) {
			Contact item = (Contact) i.next();
		    if(item.getName().equals(name))
		    	nowIn = true;
		}
		Assert.assertTrue(nowIn);
	}
	
	//The first implementation is going to have a constant ID. That of course should throw an exception. 
	@Test
	public final void addNewContactShouldBePossibleToCallRepeatedly(){
		String name = "something";
		String notes = "something";
		testCM.addNewContact(name, notes);
		testCM.addNewContact(name, notes);
	}
	
	@Test(expected = NullPointerException.class)
	public final void addPastMeetingShouldNotAcceptANullContactList() {
		testCM.addNewPastMeeting(null, pastDate, "");
	}
	
	@Test(expected = NullPointerException.class)
	public final void addPastMeetingShouldNotAcceptANullDate() {
		testContacts.add(testContact);
		testContacts.add(testContact2);
		testCM.addNewPastMeeting(testContacts, null, "");
	}
	
	@Test(expected = NullPointerException.class)
	public final void addPastMeetingShouldNotAcceptNullNotes() {
		testContacts.add(testContact);
		testContacts.add(testContact2);
		testCM.addNewPastMeeting(testContacts, pastDate , null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addPastMeetingShouldNotAcceptAFutureDate() {
		testContacts.add(testContact);
		testContacts.add(testContact2);
		testCM.addNewPastMeeting(testContacts, futureDate, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addPastMeetingShouldNotAcceptContactsThatAreNotStored() {
		testContacts.add(testContact);
		testContacts.add(testContact2);
		testCM.addNewPastMeeting(testContacts, futureDate, "");
	}
	
	//To run this test successfully I will need to implement the getPastMeetingList method.
	@Test
	public final void addPastMeetingShouldAddOneMeetingToThePastMeetingList() {
		String name = "something";
		String notes = "something";
		testCM.addNewContact(name, notes);
		int firstCount = testCM.getPastMeetingList(testCM.getContacts(name).iterator().next()).size();
		testCM.addNewPastMeeting(testCM.getContacts(name), pastDate, notes);
		int secondCount = testCM.getPastMeetingList(testCM.getContacts(name).iterator().next()).size();
		Assert.assertEquals(firstCount+1, secondCount);
	}
	
	
	
}
