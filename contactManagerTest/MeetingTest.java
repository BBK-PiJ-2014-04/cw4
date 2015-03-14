package contactManagerTest;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.*;

import contactManagerInterfaces.Meeting;

public class MeetingTest {
	
	private Meeting testMeeting;
	
	private int testID = 1;
	private Calendar date = new GregorianCalendar(2014,10,31);
	
	@Before
	public void instantiateClass() {
		testMeeting = new DummyMeetingImpl(testID);
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
	public final void GetDateShouldNotReturnNull() {
		Assert.assertNotNull(testMeeting.getDate());
	}
	
	@Test
	public final void GetDateShouldReturnTheSameDateofWhatItWasWhenInitiated() {
		Assert.assertEquals(testMeeting.getDate(),date);
	}

}
