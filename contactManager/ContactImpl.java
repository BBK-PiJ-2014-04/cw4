package contactManager;
/**
 * @see Contact
 * 
 * 
 * @author SalvatoreCardali
 *
 */

import contactManagerInterfaces.Contact;

public class ContactImpl implements Contact {
	
	private int id;
	private String name;
	private String notes;
	
	
	/**
	 * Constructor for class ContactImpl
	 * No Empty or null name allowed
	 * The id is passed but it's generated/checked by the ContactManager Class
	 * 
	 * @param id
	 * @param name
	 */
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
	/**
	 * @see @inheritDoc
	 */
	@Override
	public int getId() {
		return id;
	}
	
	/**
	 * @see @inheritDoc
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * @see @inheritDoc
	 * 
	 * Empty notes property will return a custom message
	 */
	@Override
	public String getNotes() {
		return (notes != "") ? notes : "There are no notes for this meeting";
	}
	
	/**
	 * @see @inheritDoc
	 */
	@Override
	public void addNotes(String note) {
		if(note == null) {
			throw new IllegalArgumentException("The notes can't be null");
		}
		this.notes = note;
	}

}
