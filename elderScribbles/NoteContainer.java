package elderScribbles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class NoteContainer {
	

	
	private ArrayList<Note> notes;
    private CenterPanel centerpanel;
    private ArrayList<SidePanelHeader> headers;
	private String currentNote;
	private SidePanel sidePanel;
    
    public NoteContainer(String fileName, CenterPanel centerpanel, SidePanel sidePanel) {
        this.centerpanel = centerpanel;
		currentNote = fileName;
		this.sidePanel = sidePanel;
		changeToNote(fileName);
		for (SidePanelHeader sidePanelHeader : headers) {
			System.out.println(sidePanelHeader.getText());
		}
		for (Note note : notes) {
			System.out.println(note.getNotes());
		}
    }
	
	
	public void changeToNote(String fileName){
		try {
			this.headers = new ArrayList<SidePanelHeader>();
			this.notes = new ArrayList<Note>();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                if(line.startsWith("//***")) {
                    SidePanelHeader header = new SidePanelHeader(line.substring(line.lastIndexOf("//***")+5), 2);
                    headers.add(header);
                    Note note = new Note(line.substring(line.lastIndexOf("//**")+4));
                    notes.add(note);
                    System.out.println("Note luotu ");
                }else if(line.startsWith("//**")) {
                    SidePanelHeader header = new SidePanelHeader(line.substring(line.lastIndexOf("//**")+4), 1);
                    headers.add(header);
                    Note note = new Note(line.substring(line.lastIndexOf("//**")+4));
                    notes.add(note);
                    System.out.println("Note luotu ");
                }else if(line.startsWith("//*")) {
					SidePanelHeader header = new SidePanelHeader(line.substring(line.lastIndexOf("//*")+3), 0);
                    headers.add(header);
                    Note note = new Note(line.substring(line.lastIndexOf("//**")+4));
                    notes.add(note);
                    System.out.println("Note luotu ");
                }
				else{
					notes.get(notes.size()-1).addNotes(line);
				}
                line = reader.readLine();
                System.out.println(line);
                
            }
			sidePanel.setHeaders(headers);

            
        } catch (IOException e) {
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

	public ArrayList<String> find(String searchterm){
        File file = new File(currentNote);
        ArrayList<String> found = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            String line;
            boolean match = false;
            int letter = 0;
            String currentHeader = null;
            while ((line = reader.readLine()) != null){
                if (line.startsWith("\\*")){
                    currentHeader = line.substring(line.lastIndexOf("*")+1);
                    continue;
                }
                for (int j = 0; j < line.length();j++){
                    if (!match){
                        if(line.toCharArray()[j] == searchterm.toCharArray()[0]){
                            match = true;
                            System.out.println(line.toCharArray()[j]);
                            letter++;
                            continue; 
                        }
                    }
                    else if(match && letter < searchterm.length()){
                        if(line.toCharArray()[j] == searchterm.toCharArray()[letter]){
                            System.out.println(line.toCharArray()[j]);
                            if(letter == searchterm.length() -1 && currentHeader != null){
                                found.add(currentHeader);
                            }
                            letter++;
                            continue;
                        }
                        
                    }
                    
                    else {
                        match = false;
                        letter = 0;
                    }
                }
            }
            return found;
        }catch(IOException e){
            e.printStackTrace();
        }
        return found;
    }
	
	
}
