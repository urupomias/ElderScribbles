package elderScribbles;
import java.awt.Color;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class CenterPanel extends JPanel {
    private JPanel panel;
    private JTextArea textArea;
    private Note note;
    JScrollPane scrollPane;

    public CenterPanel(){
        setLayout(new BorderLayout());
        //panel = new JPanel();
        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(200,200));
        //panel.setPreferredSize(new Dimension(250,250));
        //this.textArea = new JTextArea(44,110);
        textArea = new JTextArea();
        textArea.setPreferredSize(this.getSize());
        scrollPane = new JScrollPane(textArea); 
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(textArea,BorderLayout.CENTER);
        textArea.setEditable(true);
        
        
    }

    public String getText(){
        return textArea.getText();
    }

    public void displayNote(String note) {
        System.out.println(note);
        textArea.setText(note);
        
        //this.removeAll();
        //this.add(textArea,BorderLayout.CENTER);
        
        //this.revalidate();
        //this.repaint();
    }
    
    public JPanel getPanel(){
        return this;
    }
}

