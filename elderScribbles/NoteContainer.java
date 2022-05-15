package elderScribbles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class NoteContainer {
	

	
	private ArrayList<Note> notes;
    private CenterPanel centerpanel;
    private ArrayList<SidePanelHeader> headers;
    
    public NoteContainer(String fileName, CenterPanel centerpanel) {
        this.centerpanel = centerpanel;
        this.notes = new ArrayList<Note>();
        this.headers = new ArrayList<SidePanelHeader>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("notet.txt"));
            String line = reader.readLine();
            while (line != null) {
                if(line.equals("//*")) {
                    line = reader.readLine();
                    SidePanelHeader header = new SidePanelHeader(line, 0);
                    headers.add(header);
                    Note note = new Note();
                    notes.add(note);
                    System.out.println("Note luotu ");
                }else if(line.equals("//**")) {
                    line = reader.readLine();
                    SidePanelHeader header = new SidePanelHeader(line, 1);
                    headers.add(header);
                    Note note = new Note();
                    notes.add(note);
                    System.out.println("Note luotu ");
                }else if(line.equals("//***")) {
                    line = reader.readLine();
                    SidePanelHeader header = new SidePanelHeader(line, 2);
                    headers.add(header);
                    Note note = new Note();
                    notes.add(note);
                    System.out.println("Note luotu ");
                }
                line = reader.readLine();
                System.out.println(line);
                notes.get(0).addNotes(line);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }}
	
	
	
	
	
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
