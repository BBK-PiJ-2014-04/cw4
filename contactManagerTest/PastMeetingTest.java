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
import contactManagerInterfaces.PastMeeting;

public class PastMeetingTest {
	
	private PastMeeting testMeeting;
	
	private int testID = 1;
	private Calendar date = new GregorianCalendar(1986,07,27);
	private Set<Contact> testContacts = new HashSet<Contact>();
	private String testNotes = "testtesttest";
	
	@Before
	public void instantiateClass() {
		Contact testContact1 = new ContactImpl(1,"FirstContact");
		Contact testContact2 = new ContactImpl(2,"FirstContact2");
		testContacts.add(testContact1);
		testContacts.add(testContact2);
		testMeeting = new DummyPastMeetingImpl(testID,date,testContacts,testNotes);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public final void setNotesShouldNotAcceptaDateinTheFuture(){
		Calendar newdate = new GregorianCalendar(
										Calendar.getInstance().get(Calendar.YEAR),
										Calendar.getInstance().get(Calendar.MONTH),
										Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
										Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
										Calendar.getInstance().get(Calendar.MINUTE) + 1
									); //my program will not consider seconds
		PastMeeting newtestMeeting = new DummyPastMeetingImpl(testID,newdate,testContacts,testNotes);
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
