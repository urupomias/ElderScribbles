package elderScribbles;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.event.*;
import javax.swing.*;

public class CenterPanel {
    JPanel panel;
    JTextField textfield;
    Note note;

    public CenterPanel(){
        panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        //panel.setPreferredSize(new Dimension(250,250));
        this.textfield = new JTextField();
        //textfield.addActionListener(this);
    }

    public JPanel getPanel(){
        return panel;
    }
}

