package elderScribbles;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Menu;

import javax.swing.*;
import javax.swing.border.Border;

import javax.swing.JComboBox;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
//import org.w3c.dom.events.MouseEvent;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;




public class StartUpPane implements ActionListener{

	private JFrame frame;
	private JPanel panel;
	private String chosenNotes;
	private JButton accept;
	private JComboBox menu;
	//private NoteContainer notecontainer;
	//private TopPanel toppanel;
	
	public StartUpPane() {
		//this.toppanel = toppanel;
		//this.notecontainer = notecontainer;
		System.out.println("StartUpPane luotu");
		this.frame = new JFrame();
		this.frame.setAlwaysOnTop(true);
		this.frame.setLocationRelativeTo(null);
		this.frame.setTitle("Choose the notes...");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(600,300);
        this.frame.setLayout(new BorderLayout());
        
        //this.frame.addMouseListener(this);
		this.panel = new JPanel();
		frame.add(panel);
		File f = new File(System.getProperty("user.dir"));
		ArrayList<String> filenames = new ArrayList<>();
		
        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        };
        
        File[] files = f.listFiles(textFilter);
        for (File file : files) {
            try{
                int i = file.getCanonicalPath().lastIndexOf("\\");
                filenames.add(file.getCanonicalPath().substring(i + 1));
            }catch(Exception e){

            }
        } 
        
        filenames.add("*NEW NOTES*");
		
		//panel.add(new JLabel("Please select notes to edit or create new:"));
        //this.chosenNotes = (String) JOptionPane.showOptionDialog(JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        this.menu = new JComboBox(filenames.toArray());
        panel.add(menu);
        
        this.accept = new JButton("Accept");
        accept.addActionListener(this);
        accept.setActionCommand("accepted");
        panel.add(accept);
        this.frame.setVisible(true);
        
        
		
	}
	
	@Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals("accepted")) {
            if(menu.getSelectedItem().equals("*NEW NOTES*")) {
            	try{

                    String s = (String)JOptionPane.showInputDialog(
                            frame,
                            "Name the new note",
                            "Customized Dialog",
                            JOptionPane.PLAIN_MESSAGE
                            );

                    //If a string was returned, say so.
                    if ((s != null) && (s.length() > 0)) {
                        File newfile = new File(s + ".txt");
                        if(newfile.createNewFile()){
                            //notecontainer.changeToNote(s + ".txt");
                            chosenNotes = s + ".txt";
                        }
                        else{
                            System.out.println("Already exists");
                        }

                        //forgetting topPanel for now
                       //toppanel.updateTopPanel(); 
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
           
            }else {
            	//notecontainer.changeToNote(menu.getSelectedItem().toString());
            	this.chosenNotes = menu.getSelectedItem().toString();
            	frame.setVisible(false);
            }

        	frame.setVisible(false);
        }
    }
	
	public String getChosenNotes() {
		return chosenNotes;
	}
	
	public void setChosenNotes() {
		
	}
	
	public boolean hasFrame() {
		if(this.frame.isVisible() == false) {
			return false;
		}else {
			return true;
		}
	}
}
/*
class buttonListener implements ActionListener{
	@Override
	public void actionPerformed (ActionEvent e) {
		
	}
}*/
