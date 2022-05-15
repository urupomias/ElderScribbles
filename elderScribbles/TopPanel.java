package elderScribbles;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopPanel extends JPanel implements ActionListener {
    JPanel panel;
    MainFrame fr;
    JComboBox menu;
    ArrayList<String> filenames = new ArrayList<>();
    String currentSelection;

    public TopPanel(MainFrame fr){ 
        this.fr = fr;
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(250,30));
        //GridBagConstraints gbc = new GridBagConstraints();
        //this.setLayout(new FlowLayout(FlowLayout.LEADING,0,5));
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
            if (file.isDirectory()) {
                System.out.print("directory:");
            } else {
                System.out.print("     file:");
            }
            try{
                i = file.getCanonicalPath().lastIndexOf("\\");
                filenames.add(file.getCanonicalPath().substring(i + 1));
                System.out.println(file.getCanonicalPath().substring(i + 1));
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
        System.out.println(c);
        for (int i = 0; i < filenames.size();i++){
            if (!filenames.get(i).equals(c)){
                menu.addItem(filenames.get(i).substring(0, filenames.get(i).lastIndexOf(".")));
            }
        }
        menu.addItem("+New note");
        menu.addActionListener(this);
        menu.setPreferredSize(new Dimension(250,30));
        this.add(menu,BorderLayout.WEST);
        JTextField search = new JTextField("Search");
        this.add(search,BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    public void newNote(){

    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==menu){
            if (!menu.getSelectedItem().equals("+New note")){
                currentSelection = menu.getSelectedItem().toString();
            }
            // Centerpanel.GetNotes(menu.getSelectedItem()); ---- This tells the centerpanel to read from a specific note.
            updateTopPanel();
        }
        else newNote();
        
    }

    
}
