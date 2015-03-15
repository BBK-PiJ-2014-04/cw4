package contactManagerTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import contactManagerInterfaces.Contact;

public class ContactTest {
	
	private Contact testContact;
	private int testID = 1;
	private String testName = "testName";
	
	@Before
	public void instantiateClass() {
		testContact = new DummyContactImpl(testID,testName);
	}
	
	@Test
	public final void getIdShouldBeGreaterThanZero() {
		Assert.assertTrue(testContact.getId() > 0);
	}
	
	@Test
	public final void getIdShouldReturnTheSameIDoThatWasPassed() {
		Assert.assertEquals(testContact.getId(), testID);
	}
	
	@Test
	public final void getNameShouldNotReturnNull(){
		Assert.assertNotNull(testContact.getName());
	}
	
	@Test
	public final void getNameShouldReturnTheSameNameThatWasPassed(){
		Assert.assertEquals(testContact.getName(),testName);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void getNameShouldNotReturnEmpty(){
		testContact = new DummyContactImpl(testID, "");
	}
	
	@Test
	public final void getNotesShouldNotReturnNull(){
		Assert.assertNotNull(testContact.getNotes());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void setNotesShouldNotAcceptNull(){
		testContact.addNotes(null);
	}
	
}
