package contactManagerTest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import contactManager.ContactImpl;
import contactManager.FutureMeetingImpl;
import contactManagerInterfaces.Contact;
import contactManagerInterfaces.FutureMeeting;
public class FutureMeetingTest {

	
	private int testID = 1;
	private Set<Contact> testContacts = new HashSet<Contact>();
	
	@Before
	public void instantiateClass() {
		Contact testContact1 = new ContactImpl(1,"FirstContact");
		Contact testContact2 = new ContactImpl(2,"FirstContact2");
		testContacts.add(testContact1);
		testContacts.add(testContact2);
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
		FutureMeeting newtestMeeting = new FutureMeetingImpl(testID,newdate,testContacts);
	}
}
