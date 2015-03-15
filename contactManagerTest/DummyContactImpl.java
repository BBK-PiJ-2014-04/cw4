package contactManagerTest;
import contactManagerInterfaces.Contact;

public class DummyContactImpl implements Contact {

	private int id;
	private String name;
	private String notes;
	
	public DummyContactImpl(int id, String name) {
		this.id = id;
		this.notes = "";
		if(name != "") {
			this.name = name;
		}
		else {
			throw new IllegalArgumentException("The name of the Contact can't be null");
		}
	}
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	@Override
	public void addNotes(String note) {
		if(note == null) {
			throw new IllegalArgumentException("Null is an invalid parameter for addNotes");
		}
		this.notes = note;
	}

}
