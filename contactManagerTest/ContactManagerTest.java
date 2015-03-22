package contactManagerTest;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
	
	
	@Before
	public void instantiateClass() {
		pastDate = new GregorianCalendar(1986,07,27);
		futureDate = new GregorianCalendar(2015,07,27);
		testCM = new DummyContactManagerImpl();
	}
	
	//The following tests will require very little implementation, as they've been already handled in the implementation of the Meeting Class
	@Test(expected = IllegalArgumentException.class)
	public final void addFutureMeetingShouldNotAcceptANullContactList() {
		testCM.addFutureMeeting(null, futureDate);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addFutureMeetingShouldNotAcceptAPastDate() {
		testCM.addFutureMeeting(null, pastDate);
	}
	
}
