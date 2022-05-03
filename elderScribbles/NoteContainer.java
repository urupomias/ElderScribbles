package elderScribbles;
import java.util.ArrayList;

public class NoteContainer {
	
	ArrayList<Note> notes;
	
	public NoteContainer() {
		//Dunno if you do this here, probably not
		//ArrayList<Note> notes = new ArrayList<Note>();
	}
	
	
	public void addNote(Note notes) {
		this.notes.add(notes);
	}
	
	
}
