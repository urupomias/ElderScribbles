package elderScribbles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.plaf.ComponentUI;

import java.awt.event.*;


public class SidePanel extends JPanel implements KeyListener, ActionListener{
    private JScrollPane sPanel;
    private ArrayList<ArrayList<SidePanelHeader>> headers = new ArrayList<ArrayList<SidePanelHeader>>();
    private ArrayList<JLabel> textLabels = new ArrayList<>();
    private int currentHighlight = -1;
    private int currentSelected = -1;
    private String selectedName;
    private String textParentHeader;
    private JTextField newheader;
    private JTextField renamedheader;
    private int spacing;
    private boolean newLastHeader = false;
    private int allowedHierarchy = 2; // This determines how many levels of subheaders you can have.
    private boolean renaming = false;
    private int[] lastpos;
    private MainFrame fr;
    
    

    public SidePanel(MainFrame fr){
        this.fr = fr;
        this.setLayout(new FlowLayout(FlowLayout.LEADING,0,5));
        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(230,headers.size() * 30));
        createTestHeaders();
        
        this.setAutoscrolls(true);
        sPanel = new JScrollPane(this);
        sPanel.getVerticalScrollBar().setUnitIncrement(16);
        sPanel.setPreferredSize(new Dimension(250,250));
        addNoteheaders(headers);
        
    }

    public JScrollPane getPanel(){
        return sPanel;
    }

    public boolean clickedNew(int index, int left){
        int[] pos = getHeader(textLabels.get(index).getName());
        if (pos == null){
            return true;
        }
        int indentation = 15 + headers.get(pos[0]).get(pos[1]).getIndentation() * 15;
        if (left < indentation && left > indentation - 20){
            return true;
        }
        return false;

    }

    public void updateMouse(double x, double y){
        // Does stuff based on where the mouse is and if it was clicked.
        int height = (int)y - this.getLocationOnScreen().y; 
        int left = (int)x - this.getLocationOnScreen().x;
        spacing = height / 30;


        if(textLabels.size() > spacing && spacing >= 0){
          if (!textLabels.get(spacing).getText().equals(">>NULL<<")){
            textLabels.get(spacing).setBackground(Color.LIGHT_GRAY); // Highlights the text you're hovering over.
            if (MouseState.getInstance().getState()){ // if mouse is clicked.
                if(renaming){
                    headers.get(lastpos[0]).get(lastpos[1]).setIndentation(lastpos[2]);
                    renaming = false;
                }
                if (clickedNew(spacing, left)){// Checks if we clicked on the +, makes a new header if yes.
                    int[] pos = getHeader(">>NEW HEADER<<"); 
                    if (pos != null){   // This cleans up any previously made textboxes that weren't created.
                        headers.get(pos[0]).remove(pos[1]);
                    }
                    if(textLabels.size() == spacing + 1){
                        if(newLastHeader){
                            headers.remove(headers.size()-1);
                            newLastHeader = false;
                        }
                        
                        ArrayList<SidePanelHeader> newUpperHeader = new ArrayList<SidePanelHeader>();
                        newUpperHeader.add(new SidePanelHeader(">>NEW HEADER<<", 20));
                        headers.add(newUpperHeader);
                        newLastHeader = true;
                        this.removeAll();
                        addNoteheaders(headers);
                        this.revalidate();
                        this.repaint();
                        
                    }
                    else if(getHeader(textLabels.get(spacing).getName())[2] < allowedHierarchy){
                        headerNamer(spacing);
                    }
                    
                    MouseState.getInstance().setState(false); 
                }
                else {
                    textLabels.get(spacing).setForeground(Color.GREEN); // This highlights the selected header with green
                    selectedName = textLabels.get(spacing).getName();
                    if (currentSelected >= 0 && currentSelected != spacing){
                        textLabels.get(currentSelected).setForeground(Color.BLACK); // Unhighlights the previously selected header
                        
                    }
                    MouseState.getInstance().setState(false);
                    this.removeAll();
                    addNoteheaders(headers);
                    this.revalidate();
                    this.repaint();
                    currentSelected = spacing;
                }
            }
            else if (MouseState.getInstance().getState2()){
                popupMenu(spacing,x,y);
                MouseState.getInstance().setState2(false);
            }
          }
            if (currentHighlight != -1 && currentHighlight != spacing){ // unhighlights the stuff you're no longer hovering over.
                if (textLabels.size() > currentHighlight){
                    textLabels.get(currentHighlight).setBackground(Color.GRAY);
                }
                
            }
            currentHighlight = spacing;
        }


    }

    // Unhighlights the label if mouse outside of the panel.
    public void offScreen(){
        if (currentHighlight != -1){
            if (textLabels.size() > currentHighlight){
                textLabels.get(currentHighlight).setBackground(Color.GRAY);
            }
            currentHighlight = -1;
        }
    }

    // Right click menu.
    public void popupMenu(int index,double x,double y){
        if (index == textLabels.size()-1){
            return;
        }
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem("Rename");
        JMenuItem item2 = new JMenuItem("Delete");
        item.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                renameHeader(index);
            }
        });
        item2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                removeHeaderPopUp(index);
            }
        });
        menu.add(item);
        menu.add(item2);
        menu.show(sPanel, fr.getMousePosition().x, fr.getMousePosition().y - 50);
        //menu.show(sPanel, (int)x, (int)y - 50);
    }

    // I forgot why I made this but it works ok.. something to do with making new headers.
    private void headerNamer(int spacing){
        newLastHeader = false;
        createHeader(textLabels.get(spacing).getName(), ">>NEW HEADER<<");
    }

    // Adds all the headers in the proper order
    private void addNoteheaders(ArrayList<ArrayList<SidePanelHeader>> headerList){
        
        textLabels = new ArrayList<>();
        for (int i = 0; i < headers.size();i++){
            for (int j = 0;j < headers.get(i).size();j++){
                addLabel(headers.get(i).get(j));
            }
        } 
        this.setPreferredSize(new Dimension(230,textLabels.size() * 30 ));
        sPanel.setViewportView(this);
        addLabel(new SidePanelHeader("+ Add new header", 0));
    }

    // This adds the actual stuff on screen.
    private void addLabel(SidePanelHeader h){
        if(h.getIndentation() == 20){ // Checks if the header is a textbox
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(300,30));
            textField.setFont(new Font("Calibri", Font.BOLD, (20)));
            textField.setForeground(Color.black);
            textField.addKeyListener(this);
            
            textLabels.add(new JLabel(">>NULL<<"));
            this.add(textField);
            textField.requestFocus();
            newheader = textField;
        }
        else if(h.getIndentation() == 25){ // Check if the header is a textbox, but also getting renamed
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(300,30));
            textField.setText(headers.get(lastpos[0]).get(lastpos[1]).getText());
            textField.setFont(new Font("Calibri", Font.BOLD, (20)));
            textField.setForeground(Color.black);
            textField.addKeyListener(this);
            this.add(textField);
            textLabels.add(new JLabel(">>NULL<<"));
            renamedheader = textField;
        }
        else{   // Takes the text header given to the method, and turns it into a label, and then adds it to the screen.
            JLabel label;
            String text = "";
            
            for (int i = 0; i < h.getIndentation();i++){
                text += "    ";
            }
            label = new JLabel();
            label.setName(h.getText());
            if (h.getIndentation() < allowedHierarchy){
                text += "+ ";
            }
            label.setText(text += h.getText() + "                                                                               ");
            label.setFont(new Font("Calibri", Font.BOLD, (20)));

            if (h.getText().equals(selectedName)){
                label.setForeground(Color.green);
                currentSelected = textLabels.size()-1;
            }
            else{
                label.setForeground(Color.BLACK);
            }
            label.setOpaque(true);
            label.setBackground(Color.GRAY);
            this.add(label);
            textLabels.add(label);    
        }
    }



    /* 
    [0] = index of the arraylist of the parent header.
    [1] = index of the parent headers location.
    [2] = indentation of the header.
    [3] = location of the last subheader of the given parent header.
    */
    private int[] getHeader(String text){       
        for (int i = 0; i < headers.size();i++){
            for (int j = 0;j < headers.get(i).size();j++){
                if (headers.get(i).get(j).getText().equals(text)){
                    int[] result = new int[4];
                    result[0] = i; 
                    result[1] = j; 
                    result[2] = headers.get(i).get(j).getIndentation(); 

                    if (j == headers.get(i).size() - 1){
                        result[3] = j;
                    }
                    for (int k = j + 1; k<headers.get(i).size();k++){
                        if(headers.get(i).get(k).getIndentation() + 1 != result[2]){
                            result[3] = k - 1;  
                            break;
                        }
                    }
                    return result;
                }
            }
        }
        System.out.println("Couldnt find the header..");
        return null;
    }

    // Checks if a header with the name given exists already
    private boolean duplicateHeader(String text){
        for (int i = 0; i < headers.size();i++){
            for (int j = 0;j < headers.get(i).size();j++){
                if (headers.get(i).get(j).getText().equals(text)){
                    return true;
                }
            }
        }
        return false;
    }

    // Handles creating a new header.
    private void createHeader(String mainheader, String name){
        System.out.println(mainheader);
        int[] index = getHeader(mainheader);
        SidePanelHeader newheader;
        if(name.equals(">>NEW HEADER<<")){
            int index2[] = getHeader(">>NEW HEADER<<");
            
            if (index2 != null){
                headers.get(index2[0]).remove(index2[3]);
                index = getHeader(mainheader);
            }
            textParentHeader = mainheader;
            newheader = new SidePanelHeader(name, 20);
        }
        else {
            int number = 0;
            if (name.length() == 0){
                name = "empty";
            }
            String newname = name;
            
            while (duplicateHeader(newname)){
                number++;
                newname = name + "(" + number + ")";
            }
            newheader = new SidePanelHeader(newname, index[2] + 1);
        }
        
        headers.get(index[0]).add(index[3] + 1, newheader);;

        this.removeAll();
        addNoteheaders(headers);
        this.revalidate();
        this.repaint();
    }

    // Removes a header in the specified index(1 is highest on the sidepanel and so on), as well as all the children of said header.
    private void removeHeader(int index){
        int[] pos = getHeader(textLabels.get(index).getName());
        if (pos[1] == 0){
            headers.remove(pos[0]);
        }
        else{
            headers.get(pos[0]).remove(pos[1]);
            while(headers.get(pos[0]).size() - 1 >= pos[1]){
                if(headers.get(pos[0]).get(pos[1]).getIndentation() > pos[2]){
                    headers.get(pos[0]).remove(pos[1]);
                }
                else {
                    break;
                }
            
        }
        }
        
        this.removeAll();
        addNoteheaders(headers);
        this.revalidate();
        this.repaint();
    }

    // Creates a popupwindow to make sure you don't accidentally delete any headers.
    private void removeHeaderPopUp(int index){
        int option = JOptionPane.showConfirmDialog(fr, "Do you want to delete " + textLabels.get(index).getName() + " and all of its subheaders?", "Are you sure?", JOptionPane.YES_NO_OPTION);
        if (option == 0){
            System.out.println("Yes");
            removeHeader(index);
        }
        if(option == 1){
            System.out.println("No");
        }
    }

    public void switchToHeader(String name){
        for (int i = 0; i < textLabels.size();i++){
            if (textLabels.get(i).getName().equals(name)){
                textLabels.get(i).setForeground(Color.GREEN);
                selectedName = textLabels.get(i).getName();
            }
            if (currentSelected >= 0 && currentSelected != i){
                textLabels.get(currentSelected).setForeground(Color.BLACK);
                
            }
        }
    }

    // Renames the header in the selected position. Or more accurately turns it into a text box, which then gets renamed once the user presses enter(keyPressed method)
    private void renameHeader(int index){
        if(renaming){
            headers.get(lastpos[0]).get(lastpos[1]).setIndentation(lastpos[2]);
        }
        renaming = true;
        int[] pos = getHeader(textLabels.get(index).getName());
        lastpos = pos;
        //headers.get(pos[0]).get(pos[1]).setText(">>RENAME HEADER<<");
        headers.get(pos[0]).get(pos[1]).setIndentation(25);
        this.removeAll();
        addNoteheaders(headers);
        this.revalidate();
        this.repaint();
    }

    // These are here just to populate the sidebar.
    public void createTestHeaders(){
        ArrayList<SidePanelHeader> test = new ArrayList<>();
        
        test.add(new SidePanelHeader("Builds", 0));
        test.add(new SidePanelHeader("Agility", 1));
        headers.add(test);
        test = new ArrayList<>();
        test.add(new SidePanelHeader("Areas", 0));
        test.add(new SidePanelHeader("Castle", 1));
        test.add(new SidePanelHeader("Armory", 2));
        headers.add(test);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if(renaming){
                int number = 0;
                String text = renamedheader.getText();
                while (duplicateHeader(text)){
                    text = renamedheader.getText() + "(" + number + ")";
                    number++;
                }
                headers.get(lastpos[0]).get(lastpos[1]).setText(text);
                headers.get(lastpos[0]).get(lastpos[1]).setIndentation(lastpos[2]);
                renaming = false;
                this.removeAll();
                addNoteheaders(headers);
                this.revalidate();
                this.repaint();
            }
            else{
                if (newLastHeader){
                    int number = 0;
                    String text = newheader.getText();
                    while (duplicateHeader(text)){
                        text = newheader.getText() + "(" + number + ")";
                        number++;
                    }
                    headers.get(headers.size()-1).remove(0);
                    headers.get(headers.size()-1).add(new SidePanelHeader(text, 0));
                    newLastHeader = false;
                    newheader = null;
                    this.removeAll();
                    addNoteheaders(headers);
                    this.revalidate();
                    this.repaint();
                }
                else if (newheader != null){
                    int index2[] = getHeader(">>NEW HEADER<<");
                    headers.get(index2[0]).remove(index2[3]);
                    createHeader(textParentHeader, newheader.getText());
                    newheader = null;
                    
                }
            }   
            
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            if(renaming){
                headers.get(lastpos[0]).get(lastpos[1]).setIndentation(lastpos[2]);
                renaming = false;
            }
            else{
                int index2[] = getHeader(">>NEW HEADER<<");
                if(newLastHeader){
                    headers.remove(headers.size()-1);
                }
                else{
                    headers.get(index2[0]).remove(index2[3]);
                }
                newheader = null;
            }
            
            this.removeAll();
            addNoteheaders(headers);
            this.revalidate();
            this.repaint();
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}
