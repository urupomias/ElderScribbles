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
import java.awt.event.KeyListener;


public class MainFrame extends JFrame implements MouseListener, KeyListener{
    
    SidePanel sidePanel;

    
   

    public MainFrame(){
        //frame = new JFrame();
        this.setTitle("Elder Scribbles");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500,800);
        this.setLayout(new BorderLayout());
        this.addMouseListener(this);
        //this.addKeyListener(this);
        
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
        this.add(panel, BorderLayout.WEST);
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

    public void addPanels(SidePanel sidePanel){
        this.sidePanel = sidePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e){

    }
    @Override
    public void mousePressed(MouseEvent e){
        System.out.println("Pressed the mouse");
        if (e.getButton() == MouseEvent.BUTTON1){
            MouseState.getInstance().setState(true);
        }
        if (e.getButton() == MouseEvent.BUTTON3){
            MouseState.getInstance().setState2(true);
        }
        
    }
    @Override
    public void mouseReleased(MouseEvent e){
        
    }
    @Override
    public void mouseEntered(MouseEvent e){
        
    }
    public void mouseExited(MouseEvent e){
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }


}
