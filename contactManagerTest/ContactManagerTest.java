package contactManagerTest;

import org.junit.*;

import contactManagerInterfaces.*;

public class ContactManagerTest {
	
	@Before
	public void instantiateClass() {
		Contact testContact = new DummyContactImpl();
	}
	
}
