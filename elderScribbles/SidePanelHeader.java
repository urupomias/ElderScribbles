package elderScribbles;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import javax.swing.JButton;
import javax.swing.JLabel;


public class SidePanelHeader{
    
    private String text;
    private int indentation1 = -5;
    private int indentation2 = -5;
    private int indentation3 = -5;

    public SidePanelHeader(String text, int indentation1){
        this.text = text;
        this.indentation1 = indentation1;

    }

    public String getText(){
        return text;
    }
    public void setText(String text){
       this.text = text;
    }
    public int getIndentation(){
        return indentation1;
    }
    public void setIndentation(int indentation){
        this.indentation1 = indentation;
    }

}
