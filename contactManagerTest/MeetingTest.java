package contactManagerTest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.*;

import contactManager.ContactImpl;
import contactManagerInterfaces.Contact;
import contactManagerInterfaces.Meeting;

public class MeetingTest {
	
	private Meeting testMeeting;
	
	private int testID = 1;
	private Calendar date = new GregorianCalendar(2014,10,31);
	private Set<Contact> testContacts = new HashSet<Contact>();
	
	@Before
	public void instantiateClass() {
		Contact testContact1 = new ContactImpl(1,"FirstContact");
		Contact testContact2 = new ContactImpl(2,"FirstContact2");
		testContacts.add(testContact1);
		testContacts.add(testContact2);
		testMeeting = new DummyMeetingImpl(testID,date,testContacts);
	}
	
	@Test
	public final void getIdShouldBeGreaterThanZero() {
		Assert.assertTrue(testMeeting.getId() > 0);
	}
	
	@Test
	public final void getIdShouldReturnTheSameIDofWhatItWasWhenInitiated() {
		Assert.assertEquals(testMeeting.getId(), testID);
	}
	
	@Test
	public final void getDateShouldNotReturnNull() {
		Assert.assertNotNull(testMeeting.getDate());
	}
	
	@Test
	public final void getDateShouldReturnTheSameDateofWhatItWasWhenInitiated() {
		Assert.assertEquals(testMeeting.getDate(),date);
	}
	
	@Test
	public final void getContactsShouldNotReturnNull() {
		Assert.assertNotNull(testMeeting.getContacts());
	}
	
	@Test
	public final void getContactsShouldReturnANonEmptySet() {
		Assert.assertTrue(testMeeting.getContacts().size() > 0);
	}
	

}
