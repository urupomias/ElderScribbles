package elderScribbles;
import java.util.LinkedList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException; 
import java.util.Scanner;

public class NoteContainer {
	
	LinkedList<Note> notes;
	
	public NoteContainer(String fileName) {
		try {
		      File myObj = new File(fileName);
		      //Im putting all text from a file into a single note for now, subject to changes
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		    	  Note note = new Note();
		    	  String data = myReader.nextLine();
		    	  note.addNotes(data);
		    	if(myReader.nextLine() == "//*") {
		    		Note note = new Note();
		    	}

		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	
	
	
	
	public void NewNoteContainer(String fileName) {
		//Dunno if you do this here, probably not
		//ArrayList<Note> notes = new ArrayList<Note>();
		try {
		      File myObj = new File(fileName);
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
		
	
	
	public void addNote(Note notes) {
		this.notes.add(notes);
	}
	
	
	public void saveNotes(String fileName) {
		try {
		      FileWriter myWriter = new FileWriter(fileName);
		      myWriter.write("//*");
		      //Here we need a for -loop if theres multiple notes
		      for(int i=0; i <= notes.size(); i++) {
		    	  myWriter.write(notes.get(0).getNotes());
		    	  myWriter.write("//*");
		      }
		      
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	
}
