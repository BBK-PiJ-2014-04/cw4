package contactManagerTest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
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
		Contact testContact1 = new ContactImpl(1,"FirstContact");
		Contact testContact2 = new ContactImpl(2,"FirstContact2");
		testContacts.add(testContact1);
		testContacts.add(testContact2);
	}
	
	//The following tests will require very little implementation, as they've been already handled in the implementation of the Meeting Class
	@Test(expected = NullPointerException.class)
	public final void addFutureMeetingShouldNotAcceptANullContactList() {
		testCM.addFutureMeeting(null, futureDate);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addFutureMeetingShouldNotAcceptAPastDate() {
		testCM.addFutureMeeting(testContacts, pastDate);
	}
	
	//We need to test now if the Contacts passed to addFutureMeeting have already been added to the Contact Manager.
	//So, we will now develop tests to add Contacts, hence we're going to need a structure to save contacts in.
	
	@Test
	public final void addFutureMeetingShouldNotAcceptContactsThatAreNotStored() {
		//dependentOnTheTestonAddingContacts
		Assert.assertTrue(false);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getContactsShouldNotAcceptNull() {
		String name = null;
		testCM.getContacts(name);
	}
	
}
