package elderScribbles;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;

//import org.w3c.dom.events.MouseEvent;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MainFrame extends JFrame implements MouseListener{
    
    //JFrame frame;

    
   

    public MainFrame(){
        //frame = new JFrame();
        this.setTitle("Elder Scribbles");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500,800);
        this.setLayout(new BorderLayout());
        this.addMouseListener(this);
        
    }

    public JFrame getFrame(){
        return this;
    }

    public void addPanel(JPanel panel, String location){
        // Tän vois varmasti tehdä jotenkin järkevämmin <.<
        switch(location){
            case "WEST":
                this.add(panel, BorderLayout.WEST);
                break;
            case "NORTH":
                this.add(panel, BorderLayout.NORTH);
                break;
            case "CENTER":
                this.add(panel, BorderLayout.CENTER);
                break;

        }
        
    }

    public void addsPanel(JScrollPane panel, String location){
        // Tän vois varmasti tehdä jotenkin järkevämmin <.<
        switch(location){
            case "WEST":
                this.add(panel, BorderLayout.WEST);
                break;
            case "NORTH":
                this.add(panel, BorderLayout.NORTH);
                break;
            case "CENTER":
                this.add(panel, BorderLayout.CENTER);
                break;

        }
        
    }

    public void setVisible(){
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e){

    }
    @Override
    public void mousePressed(MouseEvent e){
        System.out.println("Pressed the mouse");
        MouseState.getInstance().setState(true);
    }
    @Override
    public void mouseReleased(MouseEvent e){
        
    }
    @Override
    public void mouseEntered(MouseEvent e){
        
    }
    public void mouseExited(MouseEvent e){
        
    }


}
