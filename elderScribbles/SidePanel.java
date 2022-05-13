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
import javax.swing.plaf.ComponentUI;

import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class SidePanel extends JPanel implements KeyListener{
    private JScrollPane sPanel;
    private ArrayList<ArrayList<SidePanelHeader>> headers = new ArrayList<ArrayList<SidePanelHeader>>();
    private ArrayList<JLabel> textLabels = new ArrayList<>();
    private int currentHighlight = -1;
    private int currentSelected = -1;
    private String selectedName;
    private String textParentHeader;
    private JTextField newheader;
    private int spacing;
    private boolean newLastHeader = false;
    
    

    public SidePanel(){
        this.setLayout(new FlowLayout(FlowLayout.LEADING,0,5));
        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(230,5000));
        createTestHeaders();
        addNoteheaders(headers);
        this.setAutoscrolls(true);
        sPanel = new JScrollPane(this);
        sPanel.getVerticalScrollBar().setUnitIncrement(16);
        sPanel.setPreferredSize(new Dimension(250,250));
        
    }

    public JScrollPane getPanel(){
        return sPanel;
    }

    public boolean clickedNew(int index, int left){
        int[] pos = getHeader(textLabels.get(index).getName());
        if (pos == null){
            return true;
        }
        int indentation = headers.get(pos[0]).get(pos[1]).getIndentation();
        switch(indentation){
            case 0:
                if (left < 15){
                    return true;
                }
                return false;
            case 1:
                if (left < 30 && left > 15){
                    return true;
                }
                return false;     
            case 2:
                if (left < 45){
                    return true;
                }
                return false; 
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
            textLabels.get(spacing).setBackground(Color.LIGHT_GRAY);
            if (MouseState.getInstance().getState()){
                if (clickedNew(spacing, left)){
                    int[] pos = getHeader(">>NEW HEADER<<");
                    if (pos != null){
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
                    else if(getHeader(textLabels.get(spacing).getName())[2] < 2){
                        headerNamer(spacing);
                    }
                    
                    MouseState.getInstance().setState(false); 
                }
                else {
                    textLabels.get(spacing).setForeground(Color.GREEN);
                    selectedName = textLabels.get(spacing).getName();
                    if (currentSelected >= 0 && currentSelected != spacing){
                        textLabels.get(currentSelected).setForeground(Color.BLACK);
                        
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
                popupMenu(spacing,left,height);
                //int index2[] = getHeader(textLabels.get(spacing).getName());
                //headers.get(index2[0]).remove(index2[3]);
                MouseState.getInstance().setState2(false);
            }
          }
            if (currentHighlight != -1 && currentHighlight != spacing){
                if (textLabels.size() > currentHighlight){
                    textLabels.get(currentHighlight).setBackground(Color.GRAY);
                }
                
            }
            currentHighlight = spacing;
        }


    }

    public void offScreen(){
        // Unhighlights the label if mouse outside of the panel.
        if (currentHighlight != -1){
            if (textLabels.size() > currentHighlight){
                textLabels.get(currentHighlight).setBackground(Color.GRAY);
            }
            currentHighlight = -1;
        }
    }
    public void popupMenu(int index,int x,int y){
        // Right click menu.
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem("Rename");
        JMenuItem item2 = new JMenuItem("Delete");
        menu.add(item);
        menu.add(item2);
        menu.show(sPanel, x, y);
    }

    private void headerNamer(int spacing){
        newLastHeader = false;
        createHeader(textLabels.get(spacing).getName(), ">>NEW HEADER<<");
    }

    private void addNoteheaders(ArrayList<ArrayList<SidePanelHeader>> headerList){
        // Adds all the headers in the proper order
        textLabels = new ArrayList<>();
        for (int i = 0; i < headers.size();i++){
            for (int j = 0;j < headers.get(i).size();j++){
                addLabel(headers.get(i).get(j));
            }
        } 
        addLabel(new SidePanelHeader("+ ADD NEW HEADER ++", 0));
    }

    private void addLabel(SidePanelHeader h){
        // This adds the actual stuff on screen.
        if(h.getIndentation() == 20){
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
        else{
            JLabel label;
            String text = "";
            
            for (int i = 0; i < h.getIndentation();i++){
                text += "    ";
            }
            label = new JLabel();
            label.setName(h.getText());
            if (h.getIndentation() < 2){
                text += "+";
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

    private void removeHeader(String name){
        // TODO: find header-> find its subheaders->remove them all.
    }

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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
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
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            int index2[] = getHeader(">>NEW HEADER<<");
            if(newLastHeader){
                headers.remove(headers.size()-1);
            }
            else{
                headers.get(index2[0]).remove(index2[3]);
            }
            newheader = null;
            this.removeAll();
            addNoteheaders(headers);
            this.revalidate();
            this.repaint();
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
}
