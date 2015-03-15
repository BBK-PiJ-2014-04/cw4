package contactManagerTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import contactManagerInterfaces.Contact;

public class ContactTest {
	
	private Contact testContact;
	private int testID = 1;
	
	@Before
	public void instantiateClass() {
		testContact = new DummyContactImpl();
	}
	
	@Test
	public final void getIdShouldBeGreaterThanZero() {
		Assert.assertTrue(testContact.getId() > 0);
	}
	
	@Test
	public final void getIdShouldReturnTheSameIDofWhatItWasWhenInitiated() {
		Assert.assertEquals(testContact.getId(), testID);
	}
	
}
