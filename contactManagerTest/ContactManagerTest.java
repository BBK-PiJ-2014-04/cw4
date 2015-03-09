package contactManagerTest;

import org.junit.*;

import contactManagerInterfaces.*;
import contactManagerClasses.*;

public class ContactManagerTest {
	
	Contact testContact = new DummyContactImpl();
	
	
	@Before
	public void instantiateClass() {
	}
	
	@Test
	public final void getIdShouldBeGreaterThanZero() {
		Assert.assertTrue(testContact.getId() > 0);
	}
	
	@Test(expected = RuntimeException.class)
	public final void instantiatenewContactClass(){
		ContactImpl testContactImpl = new ContactImpl();
	}
	
}
