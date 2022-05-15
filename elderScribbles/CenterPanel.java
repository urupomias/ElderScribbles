package elderScribbles;
import java.awt.Color;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JTextArea;

public class CenterPanel extends JPanel {
    private JPanel panel;
    private JTextArea textArea;
    private Note note;

    public CenterPanel(){
        panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setPreferredSize(new Dimension(200,200));
        //panel.setPreferredSize(new Dimension(250,250));
        this.textArea = new JTextArea(44,110);
        JScrollPane scrollPane = new JScrollPane(textArea); 
        panel.add(scrollPane);
        textArea.setEditable(true);
        
        
    }

    public void displayNote(String note) {
        textArea.setText("");
        textArea.setText(note);
    }
    
    public JPanel getPanel(){
        return panel;
    }
}

