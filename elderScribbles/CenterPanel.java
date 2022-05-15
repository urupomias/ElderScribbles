package elderScribbles;
import java.awt.Color;

import java.awt.Dimension;
import javax.swing.JPanel;

import javax.swing.*;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class CenterPanel extends JPanel {
    private JTextArea textArea;
    JScrollPane scrollPane;

    public CenterPanel(){
        setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(200,200));
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
    }
    
    public JPanel getPanel(){
        return this;
    }
}

