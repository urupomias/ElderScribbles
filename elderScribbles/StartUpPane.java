package elderScribbles;
import javax.swing.*;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;




public class StartUpPane implements ActionListener{

	private JFrame frame;
	private JPanel panel;
	private String chosenNotes;
	private JButton accept;
	private JComboBox menu;

	
	public StartUpPane() {
		System.out.println("StartUpPane luotu");
		this.frame = new JFrame();
		this.frame.setAlwaysOnTop(true);
		this.frame.setLocationRelativeTo(null);
		this.frame.setTitle("Choose the notes...");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(600,300);
        this.frame.setLayout(new BorderLayout());
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
                            chosenNotes = s + ".txt";
                        }
                        else{
                            System.out.println("Already exists");
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
           
            }else {
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
