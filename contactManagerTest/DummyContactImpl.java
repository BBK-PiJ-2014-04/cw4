package contactManagerTest;
import contactManagerInterfaces.Contact;

public class DummyContactImpl implements Contact {

	private int id;
	
	public DummyContactImpl(int id) {
		this.id = id;
	}
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNotes(String note) {
		// TODO Auto-generated method stub
		
	}

}
