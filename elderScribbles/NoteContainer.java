package elderScribbles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Scanner;

public class NoteContainer {
	

	
	//private ArrayList<Note> notes;
	private HashMap<String,Note> notes = new HashMap();
    private CenterPanel centerpanel;
    private ArrayList<SidePanelHeader> headers;
	private String currentNote;
	private SidePanel sidePanel;
	private String currentSelected;
    
    public NoteContainer(String fileName, CenterPanel centerpanel, SidePanel sidePanel) {
        this.centerpanel = centerpanel;
		
		this.sidePanel = sidePanel;
		changeToNote(fileName);
		for (SidePanelHeader sidePanelHeader : headers) {
			System.out.println(sidePanelHeader.getText());
		}
    }
	
	public void selectHeader(String header){
		if (notes.containsKey(header)){
			centerpanel.displayNote(notes.get(header).getNotes());
		}
		else{
			notes.put(header, new Note(header));
			centerpanel.displayNote(notes.get(header).getNotes());
		}
		currentSelected = header;
	}

	public void saveCurrent()throws IOException{
		System.out.println("SAVING");
		if (currentSelected != null){
			String text = centerpanel.getText();
			notes.get(currentSelected).setNotes(text);;
		File newfile = new File("tempfile.txt");
		int number = 0;
		String line = "";
		boolean writing = false;
		while(!newfile.createNewFile()){
			newfile = new File("tempfile" + number + ".txt");
			number++;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(currentNote), "UTF8"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile)));
		while((line = reader.readLine()) != null){
			if (line.startsWith("//*")){
				if(currentSelected.equals(line.substring(line.lastIndexOf("*")+1))){
					bw.write(line);
					bw.newLine();
					bw.write(text);
					bw.newLine();
					writing = true;
				}
				else{
					writing = false;
					bw.write(line);
					bw.newLine();
				}
			}
			else{
				if (!writing){
					bw.write(line);
					bw.newLine();
				}
				
			}

		}
		reader.close();
		bw.close();
		File originalfile = new File(currentNote);
		File tempfile = new File("supertemp.txt");
		originalfile.renameTo(tempfile);
		File originalfile2 = new File(currentNote);
		newfile.renameTo(originalfile2);
		tempfile.delete();
		}
		
		

	}
	
	public void changeToNote(String fileName){
		try {
			currentNote = fileName;
			this.headers = new ArrayList<SidePanelHeader>();
			//this.notes = new ArrayList<Note>();
			this.notes = new HashMap<>();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
			boolean notempty = false;
			String currentheader = "";
            while (line != null) {
                if(line.startsWith("//***")) {
                    SidePanelHeader header = new SidePanelHeader(line.substring(line.lastIndexOf("//***")+5), 2);
                    headers.add(header);
                    Note note = new Note(line.substring(line.lastIndexOf("//**")+4));
                    //notes.add(note);
					currentheader = header.getText();
					notes.put(header.getText(), note);
                    System.out.println("Note luotu ");
                }else if(line.startsWith("//**")) {
                    SidePanelHeader header = new SidePanelHeader(line.substring(line.lastIndexOf("//**")+4), 1);
                    headers.add(header);
                    Note note = new Note(line.substring(line.lastIndexOf("//**")+4));
                    currentheader = header.getText();
					notes.put(header.getText(), note);
                    System.out.println("Note luotu ");
                }else if(line.startsWith("//*")) {
					notempty = true;
					SidePanelHeader header = new SidePanelHeader(line.substring(line.lastIndexOf("//*")+3), 0);
                    headers.add(header);
                    Note note = new Note(line.substring(line.lastIndexOf("//**")+4));
                    currentheader = header.getText();
					notes.put(header.getText(), note);
                    System.out.println("Note luotu ");
                }
				else{
					if (notempty){
						//notes.get(notes.size()-1).addNotes(line);
						notes.get(currentheader).addNotes(line);;
					}
					
				}
                line = reader.readLine();
                System.out.println(line);
                
            }
			reader.close();
			sidePanel.setHeaders(headers);

            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void addHeader(String prevheader, String newheader, int indentation) throws IOException{
		if (prevheader == null){
			System.out.println("its null bro");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(currentNote)));
			bw.write("//*" + newheader);
			bw.close();
			return;
		}
		File newfile = new File("tempfile.txt");
		int number = 0;
		String line = "";
		String headerText = "//*";
		for (int i = 0; i<indentation;i++){
			headerText = headerText + "*";
		}
		headerText = headerText + newheader;
		boolean nextIsNewHeader = false;
		while(!newfile.createNewFile()){
			newfile = new File("tempfile" + number + ".txt");
			number++;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(currentNote), "UTF8"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile)));
		while((line = reader.readLine()) != null){
			if (line.startsWith("//*")){
				System.out.println("prevheader: " + prevheader);
				System.out.println("current line:" + line.substring(line.lastIndexOf("*")+1));
				if(nextIsNewHeader){
					bw.write(headerText);
					bw.newLine();
					bw.write(line);
					bw.newLine();
					nextIsNewHeader = false;
				}
				else if (prevheader.equals(line.substring(line.lastIndexOf("*")+1))){
					nextIsNewHeader = true;
					bw.write(line);
					bw.newLine();
				}
				else{
					bw.write(line);
					bw.newLine();
				}
			}
			else{
				bw.write(line);
				bw.newLine();
			}
		}
		if(nextIsNewHeader){
			bw.write(headerText);
		}
		reader.close();
		bw.close();
		File originalfile = new File(currentNote);
		File tempfile = new File("supertemp.txt");
		originalfile.renameTo(tempfile);
		File originalfile2 = new File(currentNote);
		boolean success = newfile.renameTo(originalfile2);
		tempfile.delete();
		if(success){
			System.out.println("Renamed successfully.");
		}
	}

	public void deleteHeader(String start, String end) throws IOException{
		System.out.println(start);
		System.out.println(end);
		File newfile = new File("tempfile.txt");
		int number = 0;
		String line = "";
		boolean deleting = false;
		while(!newfile.createNewFile()){
			newfile = new File("tempfile" + number + ".txt");
			number++;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(currentNote), "UTF8"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile)));
		while((line = reader.readLine()) != null){
			if (line.startsWith("//*")){
				if(start.equals(line.substring(line.lastIndexOf("*")+1))){
					deleting = true;
				}
				else if (end != null){
					if(end.equals(line.substring(line.lastIndexOf("*")+1))){
						deleting = false;
						bw.write(line);
						bw.newLine();
					}
					else if (!deleting){
						bw.write(line);
						bw.newLine();
					}					
				}
				
			}
			else{
				if (!deleting){
					bw.write(line);
					bw.newLine();
				}
				
			}

		}
		reader.close();
		bw.close();
		File originalfile = new File(currentNote);
		File tempfile = new File("supertemp.txt");
		originalfile.renameTo(tempfile);
		File originalfile2 = new File(currentNote);
		boolean success = newfile.renameTo(originalfile2);
		tempfile.delete();
		if(success){
			System.out.println("Renamed successfully.");
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
		//this.notes.add(notes);
	}
	
	public void renameHeader(String previousname, String newname) throws IOException{
		File newfile = new File("tempfile.txt");
		int number = 0;
		String line = "";
		boolean deleting = false;
		while(!newfile.createNewFile()){
			newfile = new File("tempfile" + number + ".txt");
			number++;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(currentNote), "UTF8"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile)));
		while((line = reader.readLine()) != null){
			if (line.startsWith("//*")){
				if(previousname.equals(line.substring(line.lastIndexOf("*")+1))){
					bw.write(line.substring(0, line.lastIndexOf("*")+1)+newname);
					bw.newLine();
				}
				else{
					bw.write(line);
					bw.newLine();
				}
			}
			else{
				bw.write(line);
				bw.newLine();
			}
		}
		reader.close();
		bw.close();
		File originalfile = new File(currentNote);
		File tempfile = new File("supertemp.txt");
		originalfile.renameTo(tempfile);
		File originalfile2 = new File(currentNote);
		newfile.renameTo(originalfile2);
		tempfile.delete();

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
                if (line.startsWith("//*")){
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
			reader.close();
            return found;
        }catch(IOException e){
            e.printStackTrace();
        }
        return found;
    }
	
	
}
