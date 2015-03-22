package contactManager;

import contactManagerInterfaces.Contact;

public class ContactImpl implements Contact {
	
	private int id;
	private String name;
	private String notes;
	
	public ContactImpl(int id, String name) {
		this.id = id;
		this.notes = "";
		if(name != "" && name != null) {
			this.name = name;
		}
		else {
			throw new IllegalArgumentException("The name of the Contact can't be null or empty");
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
		return (notes != "") ? notes : "There are no notes for this meeting";
	}

	@Override
	public void addNotes(String note) {
		if(note == null) {
			throw new IllegalArgumentException("The notes can't be null");
		}
		this.notes = notes;
	}

}
