package contactManagerTest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import contactManager.ContactImpl;
import contactManager.PastMeetingImpl;
import contactManagerInterfaces.Contact;
import contactManagerInterfaces.FutureMeeting;
import contactManagerInterfaces.PastMeeting;

public class FutureMeetingTest {

	private FutureMeeting testMeeting;
	
	private int testID = 1;
	private Calendar date = new GregorianCalendar(2016,07,27);
	private Set<Contact> testContacts = new HashSet<Contact>();
	
	@Before
	public void instantiateClass() {
		Contact testContact1 = new ContactImpl(1,"FirstContact");
		Contact testContact2 = new ContactImpl(2,"FirstContact2");
		testContacts.add(testContact1);
		testContacts.add(testContact2);
		testMeeting = new DummyFutureMeetingImpl(testID,date,testContacts);
	}
	//All I need to check is that the date is in the future
	@Test(expected = IllegalArgumentException.class)
	public final void setNotesShouldNotAcceptaDateinThePast(){
		Calendar newdate = new GregorianCalendar(
										Calendar.getInstance().get(Calendar.YEAR),
										Calendar.getInstance().get(Calendar.MONTH),
										Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
										Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
										Calendar.getInstance().get(Calendar.MINUTE) - 1 
									); //my program will not consider seconds
		FutureMeeting newtestMeeting = new DummyFutureMeetingImpl(testID,newdate,testContacts);
	}
}
