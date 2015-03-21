package contactManagerTest;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import contactManagerInterfaces.*;

public class ContactManagerTest {
	
	Contact testContact;
	Calendar pastdate;
	Calendar futuredate;
	
	
	@Before
	public void instantiateClass() {
		pastdate = new GregorianCalendar(1986,07,27);
		futuredate = new GregorianCalendar(1986,07,27);
	}
	
	
	
}
