package contactManagerTest;

import org.junit.*;

import contactManagerInterfaces.*;

public class ContactManagerTest {
	
	Contact testContact = new DummyContactImpl();
	
	@Before
	public void instantiateClass() {
	}
	
	@Test
	public final void getIdShouldBeGreaterThanZero() {
		Assert.assertTrue(testContact.getId() > 0);
	}
	
}