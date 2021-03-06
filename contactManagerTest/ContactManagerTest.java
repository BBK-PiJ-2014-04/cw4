package contactManagerTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import contactManager.ContactImpl;
import contactManager.ContactManagerImpl;
import contactManagerInterfaces.Contact;
import contactManagerInterfaces.ContactManager;
import contactManagerInterfaces.Meeting;
import contactManagerInterfaces.PastMeeting;

public class ContactManagerTest {
	
	Contact testContact;
	Contact testContact2;
	Contact testContact3;
	ContactManager testCM;
	Calendar pastDate;
	Calendar pastDate2;
	Calendar futureDate;
	Calendar futureDate2;
	String name;
	String notes;
	private Set<Contact> testContacts = new HashSet<Contact>();
	
	/**
	 * Instantiate preliminary actions for each test
	 */
	@Before
	public void instantiateClass() {
		pastDate = new GregorianCalendar(1986,07,27);
		pastDate2 = new GregorianCalendar(1996,07,27);
		futureDate = new GregorianCalendar(2015,07,27);
		futureDate2 = new GregorianCalendar(2025,07,27);
		testCM = new ContactManagerImpl();
		testContact = new ContactImpl(1,"FirstContact");
		testContact2 = new ContactImpl(2,"FirstContact2");
		name = "something";
		notes = "something";
	}
	
	//The following tests will require very little implementation, as they've been already handled in the implementation of the Meeting Class
	@Test(expected = IllegalArgumentException.class)
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
		Calendar futureDate2 = new GregorianCalendar(2015,07,29);
		testCM.addNewContact(name, notes);
		int firstID = testCM.addFutureMeeting(testCM.getContacts(name), futureDate);
		int secondID = testCM.addFutureMeeting(testCM.getContacts(name), futureDate2);
		Assert.assertNotEquals(firstID, secondID);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getContactsShouldNotAcceptNull() {
		name = null;
		testCM.getContacts(name);
	}
	
	@Test
	public final void getContactsShouldNotReturnNull() {
		Assert.assertNotNull(testCM.getContacts(name));
	}
	
	@Test
	public final void getContactsShouldReturnASet() {
		Assert.assertTrue(testCM.getContacts(name) instanceof Set<?>);
	}
	
	//Again, mostly of the functionality for addNewContact have already been tested for the Contact Class
	//So, the tests, will succeed with a minimum implementation.
	@Test(expected = IllegalArgumentException.class)
	public final void addNewContactShouldNotAcceptNullForName() {
		name = null;
		testCM.addNewContact(name, notes);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addNewContactShouldNotAcceptNullForNotes() {
		notes = null;
		testCM.addNewContact(name, notes);
	}
	
	@Test
	public final void addNewContactShouldAddTheContactOnTheContacsList() {
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
		testCM.addNewContact(name, notes);
		int firstCount = testCM.getPastMeetingList(testCM.getContacts(name).iterator().next()).size();
		testCM.addNewPastMeeting(testCM.getContacts(name), pastDate, notes);
		int secondCount = testCM.getPastMeetingList(testCM.getContacts(name).iterator().next()).size();
		Assert.assertEquals(firstCount+1, secondCount);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void getPastMeetingListShouldNotAcceptNull(){
		testCM.getPastMeetingList(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void getPastMeetingListShouldNotAcceptAContactThatIsNotStored() {
		testCM.getPastMeetingList(testContact);
	}
	
	@Test
	public final void getPastMeetingListShouldReturnMeetingsCronologicallySorted() {
		testCM.addNewContact(name, notes);
		testCM.addNewPastMeeting(testCM.getContacts(name), pastDate, notes);
		testCM.addNewPastMeeting(testCM.getContacts(name), pastDate2, notes);
		List<PastMeeting> testlist = testCM.getPastMeetingList(testCM.getContacts(name).iterator().next());
		Calendar prevDate = null;
		for(Iterator<PastMeeting> i = testlist.iterator(); i.hasNext(); ) {
			//I will order from the most recent till the least recent
			Calendar currentDate = i.next().getDate();
			if(prevDate == null || prevDate.compareTo(currentDate) <= 0) {
				prevDate = currentDate;
			}
			else {
				Assert.assertTrue(false);
			}
		}
		Assert.assertTrue(true);
	}
	
	@Test
	public final void getPastMeetingListShouldNotReturnAnyDuplicate() {
		testCM.addNewContact(name, notes);
		testCM.addNewContact("test1", notes);
		testCM.addNewContact("test3", notes);
		testCM.addNewContact("tmest2", notes);
		testCM.addNewPastMeeting(testCM.getContacts("test"), pastDate, notes);
		testCM.addNewPastMeeting(testCM.getContacts(name), pastDate2, notes);
		testCM.addNewPastMeeting(testCM.getContacts("test"), pastDate, notes);
		testCM.addNewPastMeeting(testCM.getContacts("st3"), pastDate2, notes);
		List<PastMeeting> testlist = testCM.getPastMeetingList(testCM.getContacts("test3").iterator().next());
		List<Integer> IDLists = new ArrayList<Integer>();
		for(Iterator<PastMeeting> i = testlist.iterator(); i.hasNext(); ) {
			//I will order from the most recent till the least recent
			Integer currentID = i.next().getId();
			if(!IDLists.contains(currentID)) {
				IDLists.add(currentID);
			}
			else {
				Assert.assertTrue(false);
			}
		}
		Assert.assertTrue(true);
	}
	
	//Now that we have all the methods that we need in order to test other methods, we can test the getContacts with IDs and then proceed in the same order in which the method are listed on the interface
	@Test(expected = IllegalArgumentException.class)
	public final void getContactsWithIDsShouldNotAcceptIDOfContactThatAreNotStored() {
		int id = 1;
		testCM.getContacts(id);
	}
	
	@Test
	public final void getContactsWithIDsShouldReturnANonEmptySet() {
		int id = 1;
		testCM.addNewContact(name, notes);
		Assert.assertTrue(testCM.getContacts(id).size() > 0);
	}
	
	@Test
	public final void getContactsWithIDsShouldAcceptIDOfContactThatAreStored() {
		int id = 1;
		testCM.addNewContact(name, notes);
		testCM.getContacts(id);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void getPastMeetingShouldNotPassAMeetingIDfromAFutureMeeting() {
		testCM.addNewContact(name, notes);
		int firstID = testCM.addFutureMeeting(testCM.getContacts(name), futureDate);
		testCM.getPastMeeting(firstID);
	}
	
	@Test
	public final void getPastMeetingShouldReturnAPastMeetingifItsNotNull() {
		testCM.addNewContact(name, notes);
		testCM.addNewPastMeeting(testCM.getContacts(name), pastDate, notes);
		PastMeeting myPastMeeting = testCM.getPastMeeting(1);
		Assert.assertNotNull(myPastMeeting.getDate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void getFutureMeetingShouldNotPassAMeetingIDfromAPastMeeting() {
		testCM.addNewContact(name, notes);
		int firstID = testCM.addFutureMeeting(testCM.getContacts(name), pastDate);
		testCM.getFutureMeeting(firstID);
	}
	
	@Test
	public final void getMeetingCanReturnAFutureMeetingifItsNotNull() {
		testCM.addNewContact(name, notes);
		testCM.addFutureMeeting(testCM.getContacts(name), futureDate);
		Meeting myMeetingInTheFuture = testCM.getMeeting(1);
		Assert.assertNotNull(myMeetingInTheFuture.getDate());
	}
	
	@Test
	public final void getMeetingCanReturnAPastMeetingifItsNotNull() {
		testCM.addNewContact(name, notes);
		testCM.addNewPastMeeting(testCM.getContacts(name), pastDate, notes);
		Meeting myMeetingInThePast = testCM.getMeeting(1);
		Assert.assertNotNull(myMeetingInThePast.getDate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void getFutureMeetingListShouldNotAcceptNull(){
		Contact myContact = null;
		testCM.getFutureMeetingList(myContact);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void getFutureMeetingListShouldNotAcceptAContactThatIsNotStored() {
		testCM.getFutureMeetingList(testContact);
	}
	
	@Test
	public final void getFutureMeetingListShouldReturnMeetingsCronologicallySorted() {
		testCM.addNewContact(name, notes);
		testCM.addFutureMeeting(testCM.getContacts(name), futureDate);
		testCM.addFutureMeeting(testCM.getContacts(name), futureDate2);
		testCM.addFutureMeeting(testCM.getContacts(name), futureDate);
		List<Meeting> testlist = testCM.getFutureMeetingList(testCM.getContacts(name).iterator().next());
		Calendar prevDate = null;
		for(Iterator<Meeting> i = testlist.iterator(); i.hasNext(); ) {
			//I will order from the most recent till the least recent
			Calendar currentDate = i.next().getDate();
			if(prevDate == null || prevDate.compareTo(currentDate) <= 0) {
				prevDate = currentDate;
			}
			else {
				Assert.assertTrue(false);
			}
		}
		Assert.assertTrue(true);
	}
	
	@Test
	public final void getFutureMeetingListShouldNotReturnAnyDuplicate() {
		testCM.addNewContact(name, notes);
		testCM.addNewContact("test1", notes);
		testCM.addNewContact("test3", notes);
		testCM.addNewContact("tmest2", notes);
		testCM.addFutureMeeting(testCM.getContacts("test"), futureDate);
		testCM.addFutureMeeting(testCM.getContacts(name), futureDate2);
		testCM.addFutureMeeting(testCM.getContacts("test"), futureDate);
		testCM.addFutureMeeting(testCM.getContacts("st3"), futureDate2);
		List<Meeting> testlist = testCM.getFutureMeetingList(testCM.getContacts("test3").iterator().next());
		List<Integer> IDLists = new ArrayList<Integer>();
		for(Iterator<Meeting> i = testlist.iterator(); i.hasNext(); ) {
			//I will order from the most recent till the least recent
			Integer currentID = i.next().getId();
			if(!IDLists.contains(currentID)) {
				IDLists.add(currentID);
			}
			else {
				Assert.assertTrue(false);
			}
		}
		Assert.assertTrue(true);
	}
	
	
	@Test
	public final void getMeetingListShouldReturnMeetingsCronologicallySorted() {
		pastDate = new GregorianCalendar(2014,12,20,15,30);
		pastDate2 = new GregorianCalendar(2014,12,20,16,30);
		Calendar pastDate3 = new GregorianCalendar(2014,12,20,16,0);
		testCM.addNewContact(name, notes);
		testCM.addNewContact("test1", notes);
		testCM.addNewContact("test3", notes);
		testCM.addNewContact("tmest2", notes);
		testCM.addNewPastMeeting(testCM.getContacts("test"), pastDate, notes);
		testCM.addFutureMeeting(testCM.getContacts(name), futureDate2);
		testCM.addNewPastMeeting(testCM.getContacts("test"), pastDate3, notes);
		testCM.addNewPastMeeting(testCM.getContacts("st3"), pastDate2, notes);
		List<Meeting> testlist = testCM.getFutureMeetingList(pastDate3);
		Calendar prevDate = null;
		for(Iterator<Meeting> i = testlist.iterator(); i.hasNext(); ) {
			//I will order from the most recent till the least recent
			Calendar currentDate = i.next().getDate();
			if(prevDate == null || prevDate.compareTo(currentDate) <= 0) {
				prevDate = currentDate;
			}
			else {
				Assert.assertTrue(false);
			}
		}
		Assert.assertTrue(true);
	}
	
	@Test
	public final void getMeetingListShouldNotReturnAnyDuplicate() {
		pastDate = new GregorianCalendar(2014,12,20,15,30);
		pastDate2 = new GregorianCalendar(2014,12,20,16,30);
		Calendar pastDate3 = new GregorianCalendar(2014,12,20,16,0);
		testCM.addNewContact(name, notes);
		testCM.addNewContact("test1", notes);
		testCM.addNewContact("test3", notes);
		testCM.addNewContact("tmest2", notes);
		testCM.addNewPastMeeting(testCM.getContacts("test"), pastDate, notes);
		testCM.addFutureMeeting(testCM.getContacts(name), futureDate2);
		testCM.addNewPastMeeting(testCM.getContacts("test"), pastDate3, notes);
		testCM.addNewPastMeeting(testCM.getContacts("st3"), pastDate2, notes);
		List<Meeting> testlist = testCM.getFutureMeetingList(pastDate3);
		List<Integer> IDLists = new ArrayList<Integer>();
		for(Iterator<Meeting> i = testlist.iterator(); i.hasNext(); ) {
			//I will order from the most recent till the least recent
			Integer currentID = i.next().getId();
			if(!IDLists.contains(currentID)) {
				IDLists.add(currentID);
			}
			else {
				Assert.assertTrue(false);
			}
		}
		Assert.assertTrue(true);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addMeetingNotesShouldNotAcceptIDsOfMeetingsWhichDoNotExist(){
		testCM.addMeetingNotes(1, notes);
	}
	
	@Test(expected = IllegalStateException.class)
	public final void addMeetingNotesShouldNotAcceptAFutureMeetingID(){
		testCM.addNewContact(name, notes);
		testCM.addMeetingNotes(testCM.addFutureMeeting(testCM.getContacts(name), futureDate), notes);
	}
	
	@Test(expected = NullPointerException.class)
	public final void addMeetingNotesShouldNotAcceptANullNotes(){
		testCM.addNewContact(name, notes);
		testCM.addNewPastMeeting(testCM.getContacts(name), pastDate, "");
		testCM.addMeetingNotes(1, null);
	}
	
	@Test
	public final void addMeetingNotesShouldActuallySaveTheNotes(){
		testCM.addNewContact(name, notes);
		testCM.addNewPastMeeting(testCM.getContacts(name), pastDate, "");
		testCM.addMeetingNotes(1, notes);
		Assert.assertTrue(testCM.getPastMeeting(1).getNotes().equals(notes));
	}
	
	
	
	@Test
	public final void theClassShouldHaveTheSameContactListAfterSaveAndLoad() {
		String Contactfile = System.getProperty("user.dir") +"\\ContactManager\\ContactsXML\\contacts.xml";
		//testCM = new DummyContactManagerImpl(Contactfile);
		testCM.addNewContact(name, notes);
		testCM.addNewContact("test1", notes);
		testCM.addNewPastMeeting(testCM.getContacts("test"), pastDate, notes);
		testCM.addFutureMeeting(testCM.getContacts(name), futureDate2);
		testCM.addNewPastMeeting(testCM.getContacts("test"), pastDate2, notes);
		testCM.flush();
		testCM = new DummyContactManagerImpl(Contactfile);
		Assert.assertTrue(testCM.getContacts(1,2).size() == 2); //error here!!
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void theClassShouldHaveEmptyListsWhenImplementedWithNoParameters() {
		testCM = new DummyContactManagerImpl();
		testCM.getContacts(1);
	}
	
}
