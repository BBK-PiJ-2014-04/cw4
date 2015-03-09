package contactManagerTest;

import org.junit.*;

import contactManagerInterfaces.ContactManager;

public class ContactManagerTest {
	
	@Before
	public void instantiateClass() {
		ContactManager testManager = new DummyContactManagerImpl();
	}
	
}
