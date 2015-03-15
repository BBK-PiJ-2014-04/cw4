package contactManagerTest;
import contactManagerInterfaces.Contact;

public class DummyContactImpl implements Contact {

	private int id;
	private String name;
	
	public DummyContactImpl(int id, String name) {
		this.id = id;
		this.name = name;
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
