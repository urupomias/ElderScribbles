package elderScribbles;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.BorderLayout;


public class MainFrame {
    
    JFrame frame;

    public MainFrame(){
        frame = new JFrame();
        frame.setTitle("Elder Scribbles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500,800);
        frame.setLayout(new BorderLayout());
        
    }

    public JFrame getFrame(){
        return frame;
    }

    public void addPanel(JPanel panel, String location){
        // Tän vois varmasti tehdä jotenkin järkevämmin <.<
        switch(location){
            case "WEST":
                frame.add(panel, BorderLayout.WEST);
                break;
            case "NORTH":
                frame.add(panel, BorderLayout.NORTH);
                break;
            case "CENTER":
                frame.add(panel, BorderLayout.CENTER);
                break;

        }
        
    }

    public void setVisible(){
        frame.setVisible(true);
    }


}
