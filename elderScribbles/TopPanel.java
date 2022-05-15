package elderScribbles;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class TopPanel extends JPanel implements ActionListener, KeyListener {
    JPanel panel;
    MainFrame fr;
    JComboBox menu;
    ArrayList<String> filenames = new ArrayList<>();
    String currentSelection;
    JTextField search;
    JPopupMenu popupmenu;
    SidePanel sidePanel;
    NoteContainer noteContainer;

    public TopPanel(MainFrame fr, SidePanel sidePanel, NoteContainer noteContainer, String filename){ 
        this.fr = fr;
        this.noteContainer = noteContainer;
        this.sidePanel = sidePanel;
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(250,30));
        setLayout(new BorderLayout());
        String name;
        int i;
        
        
        


        File f = new File(System.getProperty("user.dir"));

        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        };

        File[] files = f.listFiles(textFilter);
        for (File file : files) {
            try{
                i = file.getCanonicalPath().lastIndexOf("\\");
                filenames.add(file.getCanonicalPath().substring(i + 1));
                //System.out.println(file.getCanonicalPath().substring(i + 1));
            }catch(Exception e){

            }
        }
        if(filenames.size() > 0){
            currentSelection = filenames.get(filenames.size()-1).substring(0, filenames.get(filenames.size()-1).lastIndexOf("."));
        }
        updateTopPanel();
    }


    public void updateTopPanel(){
        this.removeAll();
        menu = new JComboBox();
        if (currentSelection != null){
            menu.addItem(currentSelection);
        }
        String c = currentSelection + ".txt";
        for (int i = 0; i < filenames.size();i++){
            if (!filenames.get(i).equals(c)){
                menu.addItem(filenames.get(i).substring(0, filenames.get(i).lastIndexOf(".")));
            }
        }
        menu.addItem("+New note");
        menu.addActionListener(this);
        menu.setPreferredSize(new Dimension(250,30));
        this.add(menu,BorderLayout.WEST);
        search = new JTextField("Search");
        search.addKeyListener(this);
        this.add(search,BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    public void newNote(){
        try{

            String s = (String)JOptionPane.showInputDialog(
                    fr,
                    "Name the new note",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE
                    );

            //If a string was returned, say so.
            if ((s != null) && (s.length() > 0)) {
                File newfile = new File(s + ".txt");
                if(newfile.createNewFile()){
                    filenames.add(s + ".txt");
                    currentSelection = s;
                    noteContainer.changeToNote(s + ".txt");
                }
                else{
                    System.out.println("Already exists");
                }


                
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e){
        
        if (e.getSource()==menu){
            System.out.println();
            
            if (!menu.getSelectedItem().equals("+New note")){
                currentSelection = menu.getSelectedItem().toString();
                noteContainer.changeToNote(menu.getSelectedItem().toString() + ".txt");
            }
            
            // Centerpanel.GetNotes(menu.getSelectedItem()); ---- This tells the centerpanel to read from a specific note.
            else{
                newNote();
            } 
            updateTopPanel();
        }
        else{
            sidePanel.switchToHeader(e.getActionCommand());
        }
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!search.getText().equals("Search")){
                ArrayList<String> found = new ArrayList<>();
                //found.add("Castle"); // REMOVE THIS LATER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                found = noteContainer.find(search.getText()); //--- This will fetch the headers with the included text.
                //ProFinder finde = new ProFinder();
                //found = finde.find(search.getText());
                popupmenu = new JPopupMenu();
                for (String string : found) {
                    JMenuItem item = new JMenuItem(string);
                    item.addActionListener(this);
                    popupmenu.add(item);
                }
                popupmenu.show(search,0, 30);
            }
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        
    }

    
}
