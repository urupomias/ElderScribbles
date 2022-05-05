package elderScribbles;

public class Note {
	
	String notes;
	
	public Note() {
		this.notes = "";
	}
	
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public void addNotes(String notes) {
		this.notes = this.notes + notes;
	}
	
}
