package contactManagerTest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import contactManager.ContactImpl;
import contactManagerInterfaces.Contact;

public class PastMeetingTest {
	
	private DummyPastMeetingImpl testMeeting;
	
	private int testID = 1;
	private Calendar date = new GregorianCalendar(2014,10,31);
	private Set<Contact> testContacts = new HashSet<Contact>();
	private String testNotes = "testtesttest";
	
	@Before
	public void instantiateClass() {
		Contact testContact1 = new ContactImpl(1,"FirstContact");
		Contact testContact2 = new ContactImpl(2,"FirstContact2");
		testContacts.add(testContact1);
		testContacts.add(testContact2);
		testMeeting = new DummyPastMeetingImpl(testID,date,testContacts);
	}
	
	@Test
	public final void getNotesShouldNotReturnNull(){
		Assert.assertNotNull(testMeeting.getNotes());
	}
	
	@Test
	public final void getNotesShouldReturnTheSameNoteSetWithAddNotes(){
		Assert.assertEquals(testMeeting.getNotes(),testNotes);
	}

}
