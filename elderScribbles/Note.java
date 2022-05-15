package elderScribbles;

public class Note {
	
	String notes;
	String header;
	
	public Note(String header) {
		this.notes = "";
		this.header = header;
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

	public String getHeader(){
		return header;
	}
	public void setHeadet(String header){
		this.header = header;
	}
	
}
