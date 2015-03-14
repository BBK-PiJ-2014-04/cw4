package contactManagerTest;

import org.junit.*;

import contactManagerInterfaces.Meeting;

public class MeetingTest {
	
	private Meeting testMeeting;
	
	private int testID = 1;
	
	@Before
	public void instantiateClass() {
		testMeeting = new DummyMeetingImpl(testID);
	}
	
	@Test
	public final void getIdShouldBeGreaterThanZero() {
		Assert.assertTrue(testMeeting.getId() > 0);
	}
	
	@Test
	public final void getIdShouldgiveUsTheSameIDWhenWeInstantiated() {
		Assert.assertTrue(testMeeting.getId(), testID);
	}

}
