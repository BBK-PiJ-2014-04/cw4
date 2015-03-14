package contactManagerTest;

import org.junit.*;

import contactManagerInterfaces.Meeting;

public class MeetingTest {
	
	Meeting testMeeting = new DummyMeetingImpl();
	
	@Before
	public void instantiateClass() {
	}
	
	
	@Test
	public final void getIdShouldBeGreaterThanZero() {
		Assert.assertTrue(testMeeting.getId() > 0);
	}

}
